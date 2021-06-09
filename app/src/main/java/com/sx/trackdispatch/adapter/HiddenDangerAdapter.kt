package com.sx.trackdispatch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemHiddenDangerBinding

class HiddenDangerAdapter: SimpleDataBindingAdapter<String, ItemHiddenDangerBinding> {

    constructor(context: Context?) : super(context, R.layout.item_hidden_danger, DiffUtils.instance.getHiddenDangerCallback()!!)

    override fun onBindItem(
            binding: ItemHiddenDangerBinding,
            item: String,
            holder: RecyclerView.ViewHolder
    ) {
//        binding.bean = item
    }
}