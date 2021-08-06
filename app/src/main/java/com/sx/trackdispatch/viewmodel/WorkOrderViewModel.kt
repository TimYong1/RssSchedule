package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkOrderViewModel: ViewModel() {
    val notifyCurrentListChanged = MutableLiveData<Boolean>().apply {
        value = true
    }

    val notifyCurrentImgListChanged = MutableLiveData<Boolean>().apply {
        value = true
    }
    val workProgressListChanged = MutableLiveData<Boolean>().apply {
        value = true
    }

    val list = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf("","","")
    }

    val imgList = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf("","","","","","","")
    }
    val workProgressList = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf("","","","","","","")
    }

    val currentWorkOrder = MutableLiveData<String>()

    val examineState = MutableLiveData<Int>().apply {
        value = -1
    }
}