package com.sx.trackdispatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sx.trackdispatch.model.TransferOrderBean

class TransferOrderDetailViewModel: ViewModel() {
    var bean = MutableLiveData<TransferOrderBean>()
}