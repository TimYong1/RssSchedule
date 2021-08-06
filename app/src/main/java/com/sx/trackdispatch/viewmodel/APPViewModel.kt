package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sx.trackdispatch.model.ProjectBean

class APPViewModel : ViewModel() {
    var currentProjectId = MutableLiveData<String>()
}