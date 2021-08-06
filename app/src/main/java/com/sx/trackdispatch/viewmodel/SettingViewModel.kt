package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sx.trackdispatch.model.ProjectBean

class SettingViewModel: ViewModel() {
    var state = MutableLiveData<Boolean>().apply { value = false }
    var projectList = MutableLiveData<MutableList<ProjectBean>>().apply { value = mutableListOf() }
    var currentProject = MutableLiveData<ProjectBean>()
}