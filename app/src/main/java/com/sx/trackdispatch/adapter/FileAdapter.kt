package com.sx.trackdispatch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemFileBinding

class FileAdapter: SimpleDataBindingAdapter<String, ItemFileBinding> {
    private var itemClick:ItemClickListener

    constructor(context: Context?,itemClick:ItemClickListener) : super(context, R.layout.item_file, DiffUtils.instance.getFileCallback()!!){
        this.itemClick = itemClick
    }

    override fun onBindItem(
            binding: ItemFileBinding,
            item: String,
            holder: RecyclerView.ViewHolder
    ) {
        binding.bean = item
        binding.itemClick = itemClick
    }

    interface ItemClickListener{
        fun add()
        fun delete(bean:String)
    }
}