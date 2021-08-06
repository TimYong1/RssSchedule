package com.sx.trackdispatch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.*

class WorkOrderProgressAdapter: SimpleDataBindingAdapter<String, ItemWorkOrderProgressBinding> {

    constructor(context: Context?) : super(context, R.layout.item_work_order_progress, DiffUtils.instance.getWorkOrderProgressCallback()!!)

    override fun onBindItem(
            binding: ItemWorkOrderProgressBinding,
            item: String,
            holder: RecyclerView.ViewHolder
    ) {
//        binding.bean = item
    }
}