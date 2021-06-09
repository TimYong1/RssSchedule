package com.sx.trackdispatch.view

import android.os.Bundle
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.ChatMsgAdapter
import com.sx.trackdispatch.adapter.ChatMsgContentAdapter
import com.sx.trackdispatch.databinding.FragmentChatMessageBinding
import com.sx.trackdispatch.viewmodel.ChatMessageViewModel

class ChatMessageFragment : BaseFragment<FragmentChatMessageBinding, ChatMessageViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_chat_message
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.msgListAdapter = ChatMsgAdapter(this.context)
        binding.msgAdapter = ChatMsgContentAdapter(this.context)
        binding.vm = mViewModel
    }
}