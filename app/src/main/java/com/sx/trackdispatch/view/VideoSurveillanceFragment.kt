package com.sx.trackdispatch.view

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentTransaction
import com.example.common.utils.LogUtil
import com.ezvizuikit.open.EZUIError
import com.ezvizuikit.open.EZUIKit
import com.ezvizuikit.open.EZUIPlayer
import com.sx.base.AppConfig
import com.sx.base.BaseFragment
import com.sx.base.net.HttpBaseObserver
import com.sx.base.net.HttpInterceptor
import com.sx.base.net.HttpUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.VideoAdapter
import com.sx.trackdispatch.api.VideoApi
import com.sx.trackdispatch.databinding.FragmentVideoSurveillanceBinding
import com.sx.trackdispatch.dialog.LoadingDialog
import com.sx.trackdispatch.model.VideoTokenBean
import com.sx.trackdispatch.model.VidepResponseBase
import com.sx.trackdispatch.viewmodel.VideoSurveillanceViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.HashMap

class VideoSurveillanceFragment : BaseFragment<FragmentVideoSurveillanceBinding, VideoSurveillanceViewModel>() {
    private lateinit var videoApi: VideoApi
    @Volatile
    private var isShow = false
    private lateinit var loadingDialog:LoadingDialog

    override fun getLayoutId(): Int {
        return R.layout.fragment_video_surveillance
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.adapter = VideoAdapter(this.context,itemClickListener)
        binding.click = ClickProxy()
        videoApi = HttpUtil.getRetrofit(VideoApi.url).create(VideoApi::class.java)
        loadingDialog = LoadingDialog.Builder().create(this.context!!)
        getToken()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isShow = hidden
        try {
            if(mViewModel.videoState.value!=VideoSurveillanceViewModel.VideoState.UNPREPARED){
                if(!hidden){
                    binding.playerUi.stopPlay()
                    mViewModel.videoState.value = VideoSurveillanceViewModel.VideoState.STOP
                }else{
//                binding.playerUi.startPlay()
                    play()
                }
            }
        }catch (e:NullPointerException){
            e.printStackTrace()
        }
    }

    private fun play(){
        if(mViewModel.videoState.value!=VideoSurveillanceViewModel.VideoState.UNPREPARED){
            binding.playerUi.setUrl(mViewModel.playUrl.value)
        }else{
            getToken()
        }
    }

    private val itemClickListener = object : VideoAdapter.ItemClickListener{
        override fun click(bean: String) {
            mViewModel.playUrl.value = bean
            play()
        }
    }

    private fun getToken(){
        val params = HashMap<String,String>()
        params["appKey"] = AppConfig.YSAPPKey
        params["appSecret"] = AppConfig.YSSecret
        loadingDialog.show()
        videoApi.accessToken(HttpInterceptor.initFormBodyBuilder(params).build())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpBaseObserver<VidepResponseBase<VideoTokenBean>>() {
                override fun failed(e: Throwable?) {
                    mViewModel.videoState.value = VideoSurveillanceViewModel.VideoState.UNPREPARED
                    loadingDialog.dismiss()
                }

                override fun succeed(t: VidepResponseBase<VideoTokenBean>?) {
                    loadingDialog.dismiss()
                    t?.let {
                        if (it.code.toInt() == 200) {
                            LogUtil.d("accessToken:" + it.data?.accessToken!!)
                            EZUIKit.setAccessToken(it.data?.accessToken)
                            binding.playerUi.setCallBack(eZUIPlayerCallBack)
                            binding.playerUi.setLoadingView(initProgressBar())
                            binding.playerUi.setUrl(mViewModel.playUrl.value!!)
                        }else{
                            mViewModel.videoState.value = VideoSurveillanceViewModel.VideoState.UNPREPARED
                        }
                    }
                }
            })
    }

    inner class ClickProxy{
        fun videoClick(){
            if(mViewModel.videoState.value!=VideoSurveillanceViewModel.VideoState.PLAYING){
                play()
            }
        }
    }

    /**
     * 创建加载view
     * @return
     */
    private fun initProgressBar(): View? {
        val relativeLayout = RelativeLayout(this.context)
        relativeLayout.setBackgroundColor(Color.parseColor("#000000"))
        val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        relativeLayout.layoutParams = lp
        val rlp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT) //addRule参数对应RelativeLayout XML布局的属性
        val mProgressBar = ProgressBar(this.context)
        mProgressBar.indeterminateDrawable = resources.getDrawable(R.drawable.video_progress)
        relativeLayout.addView(mProgressBar, rlp)
        return relativeLayout
    }

    private val eZUIPlayerCallBack = object :EZUIPlayer.EZUIPlayerCallBack{
        override fun onPlayTime(p0: Calendar?) {
//            LogUtil.d("onPlayTime")
        }

        override fun onPrepared() {
            mViewModel.videoState.value = VideoSurveillanceViewModel.VideoState.READY
            if(isShow){
                binding.playerUi.startPlay()
            }
        }

        override fun onVideoSizeChange(p0: Int, p1: Int) {
            LogUtil.d("onVideoSizeChange")
        }

        override fun onPlayFail(p0: EZUIError?) {
            LogUtil.d("onPlayFail")
            mViewModel.videoState.value = VideoSurveillanceViewModel.VideoState.FAIL
        }

        override fun onPlaySuccess() {
            LogUtil.d("onPlaySuccess")
            mViewModel.videoState.value = VideoSurveillanceViewModel.VideoState.PLAYING
        }

        override fun onPlayFinish() {
            LogUtil.d("onPlayFinish")
        }
    }

    override fun onPause() {
        super.onPause()
        binding.playerUi.stopPlay()
        mViewModel.videoState.value = VideoSurveillanceViewModel.VideoState.STOP
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //释放资源
        binding.playerUi.releasePlayer()
    }
}