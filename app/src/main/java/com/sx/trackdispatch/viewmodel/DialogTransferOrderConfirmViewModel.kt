package com.sx.trackdispatch.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogTransferOrderConfirmViewModel : ViewModel() {
    var notifyCurrentListChanged = MutableLiveData<Boolean>()
    var fileList = MutableLiveData<MutableList<String>>()
    var fileUrls = MutableLiveData<ArrayList<Uri>>()
    var MAX_FILE_SIZE = MutableLiveData<Int>()
    var confirmeState = MutableLiveData<Boolean>()

    init {
        notifyCurrentListChanged.value = true
        fileList.value = mutableListOf("1")
        fileUrls.value = ArrayList()
        MAX_FILE_SIZE.value = 4
        confirmeState.value = true
    }
}