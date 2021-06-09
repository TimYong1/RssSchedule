package com.sx.trackdispatch.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.FileAdapter
import com.sx.trackdispatch.databinding.DialogTransferOrderConfirmBinding
import com.sx.trackdispatch.viewmodel.DialogTransferOrderConfirmViewModel

class TransferOrderConfirmDialog(val activity: Activity, val lifecycle: LifecycleOwner, themeResId: Int) : Dialog(activity, themeResId) {
    private var binding: DialogTransferOrderConfirmBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_transfer_order_confirm,null,false)
    private lateinit var vm:DialogTransferOrderConfirmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = lifecycle
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application).create(DialogTransferOrderConfirmViewModel::class.java)
        binding.vm = vm
        binding.adapter = FileAdapter(activity,itemClick)
        binding.layoutManage = GridLayoutManager(activity,2)
    }

    private val itemClick = object:FileAdapter.ItemClickListener{
        override fun add() {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("*/*");//无类型限制
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            activity.startActivityForResult(intent, 1);
        }

        override fun delete(bean: String) {

        }
    }

    class Builder private constructor(){
        companion object{
            fun create(activity: Activity, lifecycle: LifecycleOwner):TransferOrderConfirmDialog{
                val dialog = TransferOrderConfirmDialog(activity,lifecycle, R.style.iosdialog)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                return dialog
            }
        }
    }
}