package com.sx.trackdispatch.view

import android.os.Bundle
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.ChatUserAdapter
import com.sx.trackdispatch.databinding.FragmentChatMessageBinding
import com.sx.trackdispatch.databinding.FragmentChatUserBinding
import com.sx.trackdispatch.viewmodel.ChatMessageViewModel
import com.sx.trackdispatch.viewmodel.ChatUserViewModel

class ChatUserFragment : BaseFragment<FragmentChatUserBinding, ChatUserViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_chat_user
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.userAdapter = ChatUserAdapter(this.context)
    }
}