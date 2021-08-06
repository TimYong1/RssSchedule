package com.sx.trackdispatch.view

import android.content.Context
import android.graphics.PixelFormat
import android.media.AudioManager
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.widget.SeekBar
import com.example.common.utils.LogUtil
import com.google.gson.GsonBuilder
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO
import com.hikvision.netsdk.RealPlayCallBack
import com.sx.base.BaseActivity
import com.sx.hikvideo.Control.DevManageGuider
import com.sx.hikvideo.Control.SDKGuider
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ActivitySettingBinding
import com.sx.trackdispatch.databinding.ActivityVideoFullBinding
import com.sx.trackdispatch.viewmodel.SettingViewModel
import com.sx.trackdispatch.viewmodel.VideoFullViewModel
import com.sx.trackdispatch.viewmodel.VideoSurveillanceViewModel
import kotlinx.coroutines.*
import java.io.File

class VideoFullActivity : BaseActivity<ActivityVideoFullBinding, VideoFullViewModel>() {
    private var m_iPreviewHandle = -1 // playback
    private val m_iSelectChannel = 1
    private val m_iSelectStreamType = 0
    private var m_iUserID = -1 // return by NET_DVR_Login_v30
    private val snapUrl = "/mnt/sdcard/dispatch/"
    private var ip = ""
    private lateinit var audioManager: AudioManager
    private lateinit var job: Job

    @Volatile
    private var isPlay = true

    override fun init() {
        binding.surfacePreviewPlay.holder.addCallback(surfaceCallBack)
        binding.surfacePreviewPlay.setZOrderOnTop(true)
        binding.click = ClickProxy()
        getIntentData()
        getVoice()
        initListener()
    }

    fun getIntentData() {
        m_iUserID = intent.getIntExtra("userid", 0)
        ip = intent.getStringExtra("ip")
        LogUtil.e("id:${m_iUserID}")
    }

    private fun getVoice() {
        audioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mViewModel.currentVolume.value = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        mViewModel.mMaxVolume.value = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        LogUtil.e("${mViewModel.currentVolume.value}  ${mViewModel.mMaxVolume.value}")
    }

    private fun initListener() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    progress,
                    AudioManager.FLAG_PLAY_SOUND
                )
                mViewModel.currentVolume.value = progress
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

    override fun getLayoutId(): Int {
        return R.layout.activity_video_full
    }

    private val surfaceCallBack = object : SurfaceHolder.Callback {
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
                    )
                ) {
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
                    )
                ) {
                    showToast("NET_DVR_RealPlaySurfaceChanged" + SDKGuider.g_sdkGuider.GetLastError_jni())
                }
            }
        }
    }

    val backClick = View.OnClickListener { finish() }

    private fun play() {
        if (this::job.isInitialized && job.isActive) {
            job.cancel()
        }
        job = GlobalScope.launch(Dispatchers.Main) {
            while (isPlay) {
                delay(300)
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
                } else {
                    isPlay = false
                    job.cancel()
//                        showToast("NET_DVR_RealPlay_V40 Succ ")
                }
            }
        }
    }

    private val playCallBack = RealPlayCallBack { var1, var2, var3, var4 ->
//        LogUtil.e("1:${var1.toString()}")
//        LogUtil.e("2:${var2.toString()}")
//        LogUtil.e("3:${var3.toString()}")
//        LogUtil.e("4:${var4.toString()}")
    }

    override fun onRestart() {
        super.onRestart()
        play()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (m_iPreviewHandle != -1) {
            SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_Stop_jni(m_iPreviewHandle)
            m_iPreviewHandle = -1
        }
        try {
            job.cancel()
        }catch (e:Exception){

        }
    }

    override fun onStop() {
        super.onStop()
        SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_Stop_jni(m_iPreviewHandle)
        try {
            job.cancel()
        }catch (e:Exception){

        }
    }

    inner class ClickProxy {
        fun fullClick() {
            finish()
        }

        fun playVideo() {
            play()
        }

        fun snapVideo() {
            snap()
        }
    }

    private fun snap() {
        val file = File(snapUrl)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir()
        }
        val imgUrl = "$snapUrl$ip.bmp"
        if (SDKGuider.g_sdkGuider.m_comPreviewGuider.RealPlay_Snap(m_iPreviewHandle, imgUrl)) {
            LogUtil.e("截图成功")
        } else {
            LogUtil.e("截图失败")
        }
    }
}