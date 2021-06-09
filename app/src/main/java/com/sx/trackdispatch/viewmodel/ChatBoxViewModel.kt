package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sx.trackdispatch.dialog.ChatBoxDialog

class ChatBoxViewModel : ViewModel() {
    var pageType = MutableLiveData<ChatBoxDialog.PageType>()

    init {
        pageType.value = ChatBoxDialog.PageType.MESSAGE
    }
}