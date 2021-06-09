package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoSurveillanceViewModel: ViewModel() {
    var notifyCurrentListChanged = MutableLiveData<Boolean>()
    var list = MutableLiveData<MutableList<String>>()

    init {
        notifyCurrentListChanged.value = true
        list.value = mutableListOf("","","","","","","","")
    }
}