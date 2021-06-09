package com.sx.trackdispatch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemHiddenDangerBinding
import com.sx.trackdispatch.databinding.ItemVideoBinding

class VideoAdapter: SimpleDataBindingAdapter<String, ItemVideoBinding> {

    constructor(context: Context?) : super(context, R.layout.item_video, DiffUtils.instance.getVideoCallback()!!)

    override fun onBindItem(
            binding: ItemVideoBinding,
            item: String,
            holder: RecyclerView.ViewHolder
    ) {
//        binding.bean = item
    }
}