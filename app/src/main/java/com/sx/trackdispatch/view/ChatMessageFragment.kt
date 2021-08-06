package com.sx.trackdispatch.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.wildfire.chat.kit.ChatManagerHolder
import cn.wildfire.chat.kit.WfcUIKit
import cn.wildfire.chat.kit.conversation.ConversationFragment
import cn.wildfire.chat.kit.conversationlist.ConversationListAdapter
import cn.wildfire.chat.kit.conversationlist.ConversationListViewModel
import cn.wildfire.chat.kit.conversationlist.ConversationListViewModelFactory
import cn.wildfire.chat.kit.conversationlist.notification.ConnectionStatusNotification
import cn.wildfire.chat.kit.conversationlist.notification.StatusNotificationViewModel
import cn.wildfirechat.client.ConnectionStatus
import cn.wildfirechat.model.Conversation
import cn.wildfirechat.remote.ChatManager
import com.example.common.utils.LogUtil
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.ChatMsgContentAdapter
import com.sx.trackdispatch.databinding.FragmentChatMessageBinding
import com.sx.trackdispatch.viewmodel.ChatMessageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class ChatMessageFragment: BaseFragment<FragmentChatMessageBinding, ChatMessageViewModel>() {
    private var adapter: ConversationListAdapter? = null
    private val types = Arrays.asList(Conversation.ConversationType.Single, Conversation.ConversationType.Group, Conversation.ConversationType.Channel)
    private val lines = Arrays.asList(0)
    private var conversationListViewModel: ConversationListViewModel? = null
    private lateinit var conversationFragment: ConversationFragment
    private lateinit var conversation:Conversation

    override fun getLayoutId(): Int {
        return R.layout.fragment_chat_message
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.msgAdapter = ChatMsgContentAdapter(this.context)
        adapter = ConversationListAdapter(this)
        conversationListViewModel = ViewModelProvider(
            this, ConversationListViewModelFactory(
                types,
                lines
            )
        ).get(ConversationListViewModel::class.java)

        conversationListViewModel!!.conversationListLiveData().observe(
            this.viewLifecycleOwner,
            androidx.lifecycle.Observer {
//            showContent()
                adapter!!.setConversationInfos(it)
            })
        binding.recyclerviewList.setAdapter(adapter)
        binding.recyclerviewList.layoutManager = LinearLayoutManager(this.context)
        (binding.recyclerviewList.getItemAnimator() as androidx.recyclerview.widget.SimpleItemAnimator?)?.setSupportsChangeAnimations(
            false
        )

        adapter?.setItemClickListener { position, conversationInfo ->
            GlobalScope.launch(Dispatchers.Main) {
                initFragment()
                delay(300)
                conversation = conversationInfo.conversation
                conversationFragment.setupConversation(
                    conversation,
                    null,
                    -1,
                    null
                )
            }
        }

        val statusNotificationViewModel = WfcUIKit.getAppScopeViewModel(StatusNotificationViewModel::class.java)
        statusNotificationViewModel.statusNotificationLiveData()
            .observe(this.viewLifecycleOwner, object : Observer<Any?> {
                override fun onChanged(t: Any?) {
                    adapter!!.updateStatusNotification(statusNotificationViewModel.notificationItems)
                }
            })
        conversationListViewModel!!.connectionStatusLiveData().observe(this.viewLifecycleOwner,
            androidx.lifecycle.Observer { status: Int? ->
                val connectionStatusNotification = ConnectionStatusNotification()
                when (status) {
                    ConnectionStatus.ConnectionStatusConnecting -> {
                        connectionStatusNotification.value = "正在连接..."
                        statusNotificationViewModel.showStatusNotification(
                            connectionStatusNotification
                        )
                    }
                    ConnectionStatus.ConnectionStatusReceiveing -> {
                        connectionStatusNotification.value = "正在同步..."
                        statusNotificationViewModel.showStatusNotification(
                            connectionStatusNotification
                        )
                    }
                    ConnectionStatus.ConnectionStatusConnected -> statusNotificationViewModel.hideStatusNotification(
                        connectionStatusNotification
                    )
                    ConnectionStatus.ConnectionStatusUnconnected -> {
                        connectionStatusNotification.value = "连接失败"
                        statusNotificationViewModel.showStatusNotification(
                            connectionStatusNotification
                        )
                    }
                    else -> {
                    }
                }
            }
        )
    }

    private fun initFragment(){
        if(!mViewModel.isAddFragment.value!!){
            conversationFragment = ConversationFragment()
            mViewModel.isAddFragment.value = true
            childFragmentManager?.beginTransaction()
                ?.add(binding.frameLayout.id, conversationFragment, "content11")
                ?.commit()
//            childFragmentManager?.beginTransaction().hide(conversationFragment)
            conversationFragment.setTitleCallBack {
                binding.tvTitle.text=it
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (adapter != null && isVisibleToUser) {
            reloadConversations()
        }
    }

    private fun reloadConversations() {
        if (ChatManager.Instance().connectionStatus == ConnectionStatus.ConnectionStatusReceiveing) {
            return
        }
        LogUtil.e("连接状态：" + ChatManager.Instance().connectionStatus)
        conversationListViewModel!!.reloadConversationList()
        conversationListViewModel!!.reloadConversationUnreadStatus()
    }

    override fun onResume() {
        super.onResume()
        reloadConversations()
    }
}