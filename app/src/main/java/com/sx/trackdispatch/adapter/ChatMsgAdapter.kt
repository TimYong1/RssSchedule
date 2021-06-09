package com.sx.trackdispatch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemChatMsgBinding
import com.sx.trackdispatch.databinding.ItemHiddenDangerBinding

class ChatMsgAdapter: SimpleDataBindingAdapter<String, ItemChatMsgBinding> {

    constructor(context: Context?) : super(context, R.layout.item_chat_msg, DiffUtils.instance.getChatMsgCallback()!!)

    override fun onBindItem(
            binding: ItemChatMsgBinding,
            item: String,
            holder: RecyclerView.ViewHolder
    ) {
//        binding.bean = item
    }
}