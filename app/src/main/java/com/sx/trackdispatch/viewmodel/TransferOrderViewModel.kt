package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sx.trackdispatch.model.TransferOrderBean

class TransferOrderViewModel: ViewModel() {
    var notifyCurrentListChanged = MutableLiveData<Boolean>()
    var list = MutableLiveData<MutableList<TransferOrderBean>>()
    var page = MutableLiveData<Int>()


    init {
        notifyCurrentListChanged.value = true
        list.value = mutableListOf()
        page.value = 1
    }
}