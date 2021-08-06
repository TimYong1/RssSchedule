package com.sx.trackdispatch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemChatMsgBinding
import com.sx.trackdispatch.databinding.ItemHiddenDangerBinding
import com.sx.trackdispatch.databinding.ItemWorkOrderBinding

class WorkOrderAdapter: SimpleDataBindingAdapter<String, ItemWorkOrderBinding> {

    constructor(context: Context?) : super(context, R.layout.item_work_order, DiffUtils.instance.getWorkOrderCallback()!!)

    override fun onBindItem(
            binding: ItemWorkOrderBinding,
            item: String,
            holder: RecyclerView.ViewHolder
    ) {
//        binding.bean = item
    }
}