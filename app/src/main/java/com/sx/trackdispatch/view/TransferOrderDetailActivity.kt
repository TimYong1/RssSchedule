package com.sx.trackdispatch.view

import android.view.View
import com.sx.base.BaseActivity
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ActivityTransferOrderDetailBinding
import com.sx.trackdispatch.dialog.TransferOrderConfirmDialog
import com.sx.trackdispatch.viewmodel.TransferOrderDetailViewModel

class TransferOrderDetailActivity: BaseActivity<ActivityTransferOrderDetailBinding, TransferOrderDetailViewModel>() {
    private lateinit var confirmDialog: TransferOrderConfirmDialog
    override fun init() {
        binding.backClick = backClick
        confirmDialog = TransferOrderConfirmDialog.Builder.create(this,this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_transfer_order_detail
    }

    val backClick = View.OnClickListener { finish() }
}