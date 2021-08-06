package com.sx.trackdispatch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemVideoBinding
import com.sx.trackdispatch.model.VideoDevice

class VideoAdapter: SimpleDataBindingAdapter<VideoDevice, ItemVideoBinding> {
    var itemClickListener:ItemClickListener

    constructor(context: Context?,litener:ItemClickListener) : super(context, R.layout.item_video, DiffUtils.instance.getVideoCallback()!!){
        this.itemClickListener = litener
    }

    override fun onBindItem(
            binding: ItemVideoBinding,
            item: VideoDevice,
            holder: RecyclerView.ViewHolder
    ) {
        binding.item = item
        binding.itemParent.setOnClickListener {
            itemClickListener.click(holder.layoutPosition,item)
        }
    }

    interface ItemClickListener{
        fun click(position:Int,bean:VideoDevice)
    }
}