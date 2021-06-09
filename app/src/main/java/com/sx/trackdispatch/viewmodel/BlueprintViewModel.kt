package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class BlueprintViewModel: ViewModel() {
    var lineData:MutableLiveData<MutableList<MutableList<Entry>>> = MutableLiveData()
    var datasets:MutableLiveData<MutableList<ILineDataSet>> = MutableLiveData()

    init {
        lineData.value = mutableListOf()
        datasets.value = mutableListOf()
        lineData.value?.add(mutableListOf(Entry(1F,1F),Entry(2F,8F),Entry(3F,4F),Entry(4F,9F)))
    }
}