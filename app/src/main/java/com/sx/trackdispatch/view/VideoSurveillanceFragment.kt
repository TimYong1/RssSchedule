package com.sx.trackdispatch.view

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SeekBar
import androidx.lifecycle.Observer
import com.example.common.utils.LogUtil
import com.example.common.utils.MMKVUtil
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hikvision.netsdk.HCNetSDK
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO
import com.sx.base.BaseFragment
import com.sx.base.net.HttpUtil
import com.sx.hikvideo.Control.DevManageGuider
import com.sx.hikvideo.Control.SDKGuider
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.VideoAdapter
import com.sx.trackdispatch.api.VideoApi
import com.sx.trackdispatch.databinding.FragmentVideoSurveillanceBinding
import com.sx.trackdispatch.dialog.LoadingDialog
import com.sx.trackdispatch.model.VideoDevice
import com.sx.trackdispatch.viewmodel.VideoSurveillanceViewModel
import kotlinx.coroutines.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class VideoSurveillanceFragment : BaseFragment<FragmentVideoSurveillanceBinding, VideoSurveillanceViewModel>() {
    private lateinit var videoApi: VideoApi
    private lateinit var loadingDialog:LoadingDialog
    private val VIDEOLIST = "VideoList"
    private var m_iPreviewHandle = -1 // playback
    private val m_iSelectChannel = 1
    private val m_iSelectStreamType = 0
    private var m_iUserID = -1 // return by NET_DVR_Login_v30
    private lateinit var audioManager:AudioManager
    private val snapUrl = "/mnt/sdcard/dispatch/"
    private lateinit var adapter:VideoAdapter
    private lateinit var job:Job
    @Volatile
    private var isPlay = true

    override fun getLayoutId(): Int {
        return R.layout.fragment_video_surveillance
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        adapter = VideoAdapter(this.context, itemClickListener)
        binding.adapter = adapter
        binding.click = ClickProxy()
        binding.controlTouchListener = touchListener
        dataListener()
        videoApi = HttpUtil.getRetrofit(VideoApi.url).create(VideoApi::class.java)
        loadingDialog = LoadingDialog.Builder().create(this.activity!!)
        loginVideo()
        binding.surfacePreviewPlay.holder.addCallback(surfaceCallBack)
        binding.surfacePreviewPlay.setZOrderOnTop(true)
        binding.surfacePreviewPlay.setZOrderMediaOverlay(true)
        getVoice()
        initListener()
    }

    private fun getVoice(){
        audioManager = this.context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mViewModel.currentVolume.value = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        mViewModel.mMaxVolume.value = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        LogUtil.e("${mViewModel.currentVolume.value}  ${mViewModel.mMaxVolume.value}")
    }

    private fun initListener() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_PLAY_SOUND)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        play()
    }

    private fun dataListener(){}

    private fun play(){
//        if(this::job.isInitialized&&job.isActive){
//            job.cancel()
//        }
        LogUtil.e("点击")
        isPlay = true
        job = GlobalScope.launch(Dispatchers.Main) {
            while (isPlay){
                delay(300)
                LogUtil.e("循环中")
                if(isOnlineOrChooseDev()){
                    if (m_iPreviewHandle != -1) {
                        SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_Stop_jni(m_iPreviewHandle)
                    }
                    val struPlayInfo = NET_DVR_PREVIEWINFO()
                    struPlayInfo.lChannel = m_iSelectChannel
                    struPlayInfo.dwStreamType = m_iSelectStreamType
                    struPlayInfo.bBlocked = 1
                    struPlayInfo.hHwnd = binding.surfacePreviewPlay.holder
                    m_iPreviewHandle = SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_V40_jni(
                        m_iUserID,
                        struPlayInfo,
                        null
                    )
                    if (m_iPreviewHandle < 0) {
//                        showToast("NET_DVR_RealPlay_V40 fail, Err:" + SDKGuider.g_sdkGuider.GetLastError_jni())
                    }else{
                        isPlay = false
                        mViewModel.videoState.value = VideoSurveillanceViewModel.VideoState.PLAYING
                        job.cancel()
//                        showToast("NET_DVR_RealPlay_V40 Succ ")
                    }
                }
            }
        }
    }

    private fun getLocalVideoList():ArrayList<VideoDevice>{
        val jsonString = MMKVUtil.getMMKV().decodeString(VIDEOLIST)
        val gson = GsonBuilder().create()
        val list: ArrayList<VideoDevice> = gson.fromJson(
            jsonString,
            object : TypeToken<ArrayList<VideoDevice?>?>() {}.type
        )
        return list
    }

    private fun setLocalVideoList(list:MutableList<VideoDevice>){
        val gson = GsonBuilder().create()
        //存储videoList
        MMKVUtil.getMMKV().encode(VIDEOLIST, gson.toJson(list))
    }

    private fun getServiceVideoList():MutableList<VideoDevice>{
        val list = mutableListOf(
            VideoDevice(
                "10.0.0.47",
                "10.0.0.47",
                "8000",
                "admin",
                "Abc123456"
            )
        )
        val gson = GsonBuilder().create()
        //存储videoList
        MMKVUtil.getMMKV().encode(VIDEOLIST, gson.toJson(list))
        return list
    }

    private fun loginVideo(){
//        mViewModel.videos.value = getServiceVideoList()
//        if(mViewModel.videos.value!!.size==0){
            mViewModel.videos.value = getLocalVideoList()
//        }
        mViewModel.videos.value!!.forEachIndexed { index, videoDevice ->
            var deviceItem: DevManageGuider.DeviceItem = SDKGuider.g_sdkGuider.m_comDMGuider.DeviceItem()
            deviceItem.m_szDevName = videoDevice.name
            deviceItem.m_struNetInfo = SDKGuider.g_sdkGuider.m_comDMGuider.DevNetInfo(
                videoDevice.ip,
                videoDevice.port,
                videoDevice.userName,
                videoDevice.password
            )

            val item = SDKGuider.g_sdkGuider.m_comDMGuider.login_v40_jna(
                deviceItem.m_szDevName,
                deviceItem.m_struNetInfo
            )
            if(item!=null){
                deviceItem = item
            }
            mViewModel.videoList.value?.add(deviceItem)
            if(index==0){
                mViewModel.currentVideoItem.value = mViewModel.videoList.value!!.get(0)
                m_iUserID = mViewModel.currentVideoItem.value!!.m_lUserID
            }
        }
    }

    private fun isOnlineOrChooseDev(): Boolean {
        if (mViewModel.currentVideoItem.value == null) {
            showToast("获取摄像头信息失败")
            return false
        } else {
            if (mViewModel.currentVideoItem.value!!.m_struDevState.m_iLogState!= 1) {
                showToast("设备连接失败")
                return false
            }
        }
        return true
    }

    private val itemClickListener = object : VideoAdapter.ItemClickListener{

        override fun click(position: Int, bean: VideoDevice) {
            mViewModel.currentVideoItem.value = mViewModel.videoList.value!!.get(position)
            mViewModel.currentPlayPosition.value = position
            play()
        }
    }

    inner class ClickProxy{
        fun videoClick(){

        }

        fun playVideo(){
            play()
        }

        fun stopPlay(){
            if(mViewModel.videoState.value==VideoSurveillanceViewModel.VideoState.STOP){
                play()
            }else{
                stop()
            }
        }

        fun snapVideo(){
            snap()
        }

        fun full(){
            LogUtil.e("id:${mViewModel.currentVideoItem.value!!.m_lUserID}")
            startActivity(Intent(this@VideoSurveillanceFragment.context,VideoFullActivity::class.java).putExtra("userid",mViewModel.currentVideoItem.value!!.m_lUserID).putExtra("ip",mViewModel.videos.value!!.get(mViewModel.currentPlayPosition.value!!).ip))
        }
    }

    private val touchListener = View.OnTouchListener { v, event ->
        Log.e("测试", "${v.tag} ${event.action}")
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                HCNetSDK.getInstance().NET_DVR_PTZControl_Other(
                    mViewModel.currentVideoItem.value!!.m_lUserID, 1,
                    v.tag.toString().toInt(), 0
                )
            }
            MotionEvent.ACTION_UP -> {
                HCNetSDK.getInstance().NET_DVR_PTZControl_Other(
                    mViewModel.currentVideoItem.value!!.m_lUserID, 1,
                    v.tag.toString().toInt(), 1
                )
            }
        }
        return@OnTouchListener true
    }

    private val surfaceCallBack = object :SurfaceHolder.Callback{
        override fun surfaceCreated(holder: SurfaceHolder?) {
            binding.surfacePreviewPlay.holder.setFormat(PixelFormat.TRANSLUCENT)
            if (-1 == m_iPreviewHandle) {
                return
            }
            val surface = holder!!.surface
            if (surface.isValid) {
                if (-1 == SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlaySurfaceChanged_jni(
                        m_iPreviewHandle,
                        0,
                        holder
                    )) {
                    showToast("NET_DVR_PlayBackSurfaceChanged" + SDKGuider.g_sdkGuider.GetLastError_jni())
                }
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            if (-1 == m_iPreviewHandle) {
                return
            }
            if (holder!!.surface.isValid) {
                if (-1 == SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlaySurfaceChanged_jni(
                        m_iPreviewHandle,
                        0,
                        null
                    )) {
                    showToast("NET_DVR_RealPlaySurfaceChanged" + SDKGuider.g_sdkGuider.GetLastError_jni())
                }
            }
        }
    }

    private fun snap(){
        val file= File(snapUrl)
        if(!file .exists() &&!file.isDirectory()){
            file.mkdir()
        }
        val name = "screenshot-${UUID.randomUUID()}"
        val imgUrl = snapUrl+name+".bmp"
        if(SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_Snap(m_iPreviewHandle, imgUrl)){
            LogUtil.e("截图成功")
            mViewModel.videos.value!!.get(mViewModel.currentPlayPosition.value!!).url = imgUrl
            setLocalVideoList(mViewModel.videos.value!!)
            adapter.notifyItemChanged(mViewModel.currentPlayPosition.value!!)
        }else{
            LogUtil.e("截图失败")
        }
    }

    private fun stop(){
        if (m_iPreviewHandle != -1) {
            SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_Stop_jni(m_iPreviewHandle)
        }
        mViewModel.videoState.value = VideoSurveillanceViewModel.VideoState.STOP
//        binding.surfacePreviewPlay.setZOrderOnTop(false)
    }

    override fun onStop() {
        super.onStop()
        stop()
    }

    fun getDisplay(): Display {
        return this.activity?.getWindowManager()!!.getDefaultDisplay()
    }
}