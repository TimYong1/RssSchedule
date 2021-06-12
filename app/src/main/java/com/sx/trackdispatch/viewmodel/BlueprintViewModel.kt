package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class BlueprintViewModel : ViewModel() {
    var lineData: MutableLiveData<MutableList<MutableList<Entry>>> = MutableLiveData()
    var datasets: MutableLiveData<MutableList<ILineDataSet>> = MutableLiveData()

    init {
        lineData.value = mutableListOf()
        datasets.value = mutableListOf()
        initLineData()
    }

    fun initLineData(){
        for (j in 0..1){
            val lines = mutableListOf<Entry>()
            for (i in 0..23){
                if(j==0){
                    lines.add(Entry(i.toFloat(),(60..100).random().toFloat()))
                }else{
                    lines.add(Entry(i.toFloat(),(20..40).random().toFloat()))
                }
            }
            lineData.value?.add(lines)
        }
    }
}