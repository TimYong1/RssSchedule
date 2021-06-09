package com.sx.trackdispatch.view

import android.os.Bundle
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.TransferOrderAdapter
import com.sx.trackdispatch.databinding.FragmentTransferOrderBinding
import com.sx.trackdispatch.dialog.TransferOrderConfirmDialog
import com.sx.trackdispatch.viewmodel.TransferOrderViewModel

class TransferOrderFragment : BaseFragment<FragmentTransferOrderBinding, TransferOrderViewModel>() {
    private lateinit var confirmDialog:TransferOrderConfirmDialog

    override fun getLayoutId(): Int {
        return R.layout.fragment_transfer_order
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.adapter = TransferOrderAdapter(this.context,itemClick)
    }

    private val itemClick = object :TransferOrderAdapter.ItemClickListener{

        override fun confirm(bean: String) {
            showDialog()
        }

        override fun reject() {
        }

        override fun execute() {
        }
    }

    fun showDialog(){
        if(!this::confirmDialog.isInitialized){
            confirmDialog = TransferOrderConfirmDialog.Builder.create(this.activity!!,this)
        }
        confirmDialog.show()
    }
}