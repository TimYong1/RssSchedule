package com.sx.trackdispatch.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sx.trackdispatch.view.ChatMessageFragment
import com.sx.trackdispatch.view.ChatUserFragment

class ChatFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val list = mutableListOf<Fragment>(ChatMessageFragment(), ChatUserFragment())

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

}