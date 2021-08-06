package com.sx.trackdispatch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemChatMsgBinding
import com.sx.trackdispatch.databinding.ItemHiddenDangerBinding
import com.sx.trackdispatch.databinding.ItemImageBinding
import com.sx.trackdispatch.databinding.ItemWorkOrderBinding

class ImageViewAdapter: SimpleDataBindingAdapter<String, ItemImageBinding> {

    constructor(context: Context?) : super(context, R.layout.item_image, DiffUtils.instance.getImageCallback()!!)

    override fun onBindItem(
            binding: ItemImageBinding,
            item: String,
            holder: RecyclerView.ViewHolder
    ) {
//        binding.bean = item
    }
}