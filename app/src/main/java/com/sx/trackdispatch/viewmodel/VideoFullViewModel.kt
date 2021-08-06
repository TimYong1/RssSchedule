package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sx.hikvideo.Control.DevManageGuider

class VideoFullViewModel: ViewModel() {
    var mMaxVolume = MutableLiveData<Int>().apply {
        value = 10
    }

    var currentVolume = MutableLiveData<Int>().apply {
        value = 0
    }
}