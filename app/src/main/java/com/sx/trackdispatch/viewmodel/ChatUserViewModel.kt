package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.wildfire.chat.kit.contact.model.UIUserInfo

class ChatUserViewModel: ViewModel() {
    var notifyCurrentListChanged = MutableLiveData<Boolean>()
    var list = MutableLiveData<MutableList<String>>()
    var currentUserInfo = MutableLiveData<UIUserInfo>()

    init {
        notifyCurrentListChanged.value = true
        list.value = mutableListOf("","","","","","","","")
    }
}