package com.sx.trackdispatch.dialog

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.common.utils.LogUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.ChatFragmentAdapter
import com.sx.trackdispatch.adapter.MainViewPagerAdapter
import com.sx.trackdispatch.databinding.DialogAnswerBinding
import com.sx.trackdispatch.databinding.DialogChatBoxBinding
import com.sx.trackdispatch.databinding.DialogIosDefaultBinding
import com.sx.trackdispatch.viewmodel.ChatBoxViewModel

class ChatBoxDialog(val activity: Activity, themeResId: Int,val fragmentManager: FragmentManager,val lifecycle: LifecycleOwner) : Dialog(activity, themeResId) {

    private var binding: DialogChatBoxBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_chat_box,null,false)
    private lateinit var vm:ChatBoxViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = lifecycle
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application).create(ChatBoxViewModel::class.java)
        binding.vm = vm
        binding.click = ClickProxy()
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.viewPager.adapter = ChatFragmentAdapter(fragmentManager,lifecycle.lifecycle)
        binding.viewPager.setUserInputEnabled(false)
    }

    class Builder private constructor(){
        companion object{
            fun create(activity: Activity,fragmentManager: FragmentManager,lifecycle: LifecycleOwner):ChatBoxDialog{
                val dialog = ChatBoxDialog(activity,R.style.iosdialog,fragmentManager,lifecycle)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                return dialog
            }
        }
    }

    enum class PageType constructor(val value:Int){
        MESSAGE(0),USER(1)
    }

    inner class ClickProxy{
        fun changePageType(type:PageType){
            vm.pageType.value = type
            binding.viewPager.currentItem = type.value
        }

        fun closeClick(){
            this@ChatBoxDialog.dismiss()
        }
    }
}