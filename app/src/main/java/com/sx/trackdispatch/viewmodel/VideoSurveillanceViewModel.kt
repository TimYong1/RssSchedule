package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sx.hikvideo.Control.DevManageGuider
import com.sx.trackdispatch.model.VideoDevice

class VideoSurveillanceViewModel : ViewModel() {
    var notifyCurrentListChanged = MutableLiveData<Boolean>()
    var videoState = MutableLiveData<VideoState>().apply {
        value = VideoState.STOP
    }
    var videoList = MutableLiveData<MutableList<DevManageGuider.DeviceItem>>().apply {
        value = mutableListOf()
    }
    var currentPlayPosition = MutableLiveData<Int>().apply {
        value = 0
    }
    var currentVideoItem = MutableLiveData<DevManageGuider.DeviceItem>()
    var videos = MutableLiveData<MutableList<VideoDevice>>().apply {
        value = mutableListOf()
    }

    var currentVolume = MutableLiveData<Int>().apply {
        value = 0
    }

    var mMaxVolume = MutableLiveData<Int>().apply {
        value = 10
    }

    init {
        notifyCurrentListChanged.value = true
    }

    enum class VideoState(var code: Int) {
        PLAYING(1),STOP(0),ERROR(-1);
    }
}