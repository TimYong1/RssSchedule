package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    var projectList:MutableLiveData<MutableList<String>> = MutableLiveData()
    var projectName:MutableLiveData<String> = MutableLiveData()
    init {
        projectList.value = mutableListOf("1111","2222","3333")
        projectName.value = "中国铁建武汉地铁1项目"
    }
}