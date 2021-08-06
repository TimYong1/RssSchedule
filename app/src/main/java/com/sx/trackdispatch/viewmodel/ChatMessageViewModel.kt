package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatMessageViewModel: ViewModel() {
    var notifyCurrentListChanged = MutableLiveData<Boolean>()
    var notifyContentCurrentListChanged = MutableLiveData<Boolean>()
    var list = MutableLiveData<MutableList<String>>()
    var listContent = MutableLiveData<MutableList<String>>()
    var isAddFragment = MutableLiveData<Boolean>().apply { value = false }

    init {
        notifyCurrentListChanged.value = true
        notifyContentCurrentListChanged.value = true
        list.value = mutableListOf("","","","","","","","")
        listContent.value = mutableListOf("0","1","0","0","1","1","1","0")
    }
}