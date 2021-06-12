package com.sx.trackdispatch.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemTransferOrderBinding
import com.sx.trackdispatch.view.TransferOrderDetailActivity

class TransferOrderAdapter: SimpleDataBindingAdapter<String, ItemTransferOrderBinding> {
    private var itemClick:ItemClickListener

    constructor(
        context: Context?,
        itemClick: ItemClickListener
    ) : super(context, R.layout.item_transfer_order, DiffUtils.instance.getTransferOrderCallback()!!) {
        this.itemClick = itemClick
    }

    override fun onBindItem(
            binding: ItemTransferOrderBinding,
            item: String,
            holder: RecyclerView.ViewHolder
    ) {
        binding.click = View.OnClickListener {
            mContext.startActivity(Intent(mContext,TransferOrderDetailActivity::class.java))
        }
        binding.itemClick = itemClick
        binding.bean = item
    }

    interface ItemClickListener{
        fun confirm(bean:String)
        fun reject()
        fun execute()
        fun speak(content:String)
    }
}