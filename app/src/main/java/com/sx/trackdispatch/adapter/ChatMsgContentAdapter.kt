package com.sx.trackdispatch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemChatMsgContentBinding
import com.sx.trackdispatch.databinding.ItemHiddenDangerBinding

class ChatMsgContentAdapter: SimpleDataBindingAdapter<String, ItemChatMsgContentBinding> {

    constructor(context: Context?) : super(context, R.layout.item_chat_msg_content, DiffUtils.instance.getChatMsgContetCallback()!!)

    override fun onBindItem(
            binding: ItemChatMsgContentBinding,
            item: String,
            holder: RecyclerView.ViewHolder
    ) {
        binding.data = item
    }
}