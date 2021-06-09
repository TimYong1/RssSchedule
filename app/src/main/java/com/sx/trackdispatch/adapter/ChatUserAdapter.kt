package com.sx.trackdispatch.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ItemChatMsgBinding
import com.sx.trackdispatch.databinding.ItemChatUserBinding
import com.sx.trackdispatch.databinding.ItemHiddenDangerBinding

class ChatUserAdapter: SimpleDataBindingAdapter<String, ItemChatUserBinding> {

    constructor(context: Context?) : super(context, R.layout.item_chat_user, DiffUtils.instance.getChatUserCallback()!!)

    override fun onBindItem(
            binding: ItemChatUserBinding,
            item: String,
            holder: RecyclerView.ViewHolder
    ) {
//        binding.bean = item
    }
}