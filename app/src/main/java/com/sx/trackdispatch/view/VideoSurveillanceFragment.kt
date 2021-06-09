package com.sx.trackdispatch.view

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.OrientationEventListener
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.example.common.utils.LogUtil
import com.ezvizuikit.open.EZUIError
import com.ezvizuikit.open.EZUIKit
import com.ezvizuikit.open.EZUIPlayer
import com.sx.base.AppConfig
import com.sx.base.BaseFragment
import com.sx.base.net.HttpInterceptor
import com.sx.base.net.HttpUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.VideoAdapter
import com.sx.trackdispatch.api.VideoApi
import com.sx.trackdispatch.databinding.FragmentVideoSurveillanceBinding
import com.sx.trackdispatch.viewmodel.VideoSurveillanceViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.HashMap

class VideoSurveillanceFragment : BaseFragment<FragmentVideoSurveillanceBinding, VideoSurveillanceViewModel>() {
    private val url = "ezopen://open.ys7.com/201392314/1.live"
    private lateinit var videoApi: VideoApi
    private var isInitVideo = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_video_surveillance
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.adapter = VideoAdapter(this.context)
        videoApi = HttpUtil.getRetrofit(VideoApi.url).create(VideoApi::class.java)
        getToken()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        LogUtil.d("fragment可见性："+isVisibleToUser)
        if(isInitVideo){
            if(!isVisibleToUser){
                binding.playerUi.stopPlay()
            }else{
                binding.playerUi.startPlay()
            }
        }
    }

    private fun getToken(){
        val params = HashMap<String,String>()
        params["appKey"] = AppConfig.YSAPPKey
        params["appSecret"] = AppConfig.YSSecret
        videoApi.accessToken(HttpInterceptor.initFormBodyBuilder(params).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.code.toInt() == 200) {
                        LogUtil.d("accessToken:" + it.data?.accessToken!!)
                        EZUIKit.setAccessToken(it.data?.accessToken)
                        binding.playerUi.setCallBack(eZUIPlayerCallBack)
                        binding.playerUi.setLoadingView(initProgressBar())
                        binding.playerUi.setUrl(url)
                        isInitVideo = true
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
        val lp = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
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
            LogUtil.d("开始播放")
            binding.playerUi.startPlay()
        }

        override fun onVideoSizeChange(p0: Int, p1: Int) {
            LogUtil.d("onVideoSizeChange")
        }

        override fun onPlayFail(p0: EZUIError?) {
            LogUtil.d("onPlayFail")
        }

        override fun onPlaySuccess() {
            LogUtil.d("onPlaySuccess")
        }

        override fun onPlayFinish() {
            LogUtil.d("onPlayFinish")
        }
    }

    override fun onPause() {
        super.onPause()
        binding.playerUi.stopPlay()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //释放资源
        binding.playerUi.releasePlayer()
    }
}