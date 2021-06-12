package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoSurveillanceViewModel : ViewModel() {
    var notifyCurrentListChanged = MutableLiveData<Boolean>()
    var list = MutableLiveData<MutableList<String>>()
    var videoState = MutableLiveData<VideoState>()
    var playUrl = MutableLiveData<String>()

    init {
        notifyCurrentListChanged.value = true
        videoState.value = VideoState.UNPREPARED
        list.value = mutableListOf(
//            "ezopen://open.ys7.com/201392314/1.live",
            "ezopen://open.ys7.com/239219669/1.hd.live",
            "ezopen://open.ys7.com/239219670/1.hd.live",
            "ezopen://open.ys7.com/239219671/1.hd.live",
            "ezopen://open.ys7.com/239219672/1.hd.live",
            "ezopen://open.ys7.com/239219673/1.hd.live",
            "ezopen://open.ys7.com/239219676/1.hd.live",
            "ezopen://open.ys7.com/239219677/1.hd.live"
        )
        playUrl.value =list.value?.get(0)
    }

    enum class VideoState{
        PLAYING,READY,FAIL,UNPREPARED,STOP
    }
}