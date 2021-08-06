package com.sx.trackdispatch.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cn.wildfire.chat.kit.WfcUIKit
import cn.wildfire.chat.kit.contact.ContactViewModel
import cn.wildfire.chat.kit.contact.UserListAdapter
import cn.wildfire.chat.kit.contact.model.UIUserInfo
import cn.wildfire.chat.kit.user.UserInfoActivity
import cn.wildfire.chat.kit.widget.QuickIndexBar
import cn.wildfirechat.model.ChannelInfo
import com.example.common.utils.LogUtil
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.ChatUserAdapter
import com.sx.trackdispatch.databinding.FragmentChatMessageBinding
import com.sx.trackdispatch.databinding.FragmentChatUserBinding
import com.sx.trackdispatch.viewmodel.ChatMessageViewModel
import com.sx.trackdispatch.viewmodel.ChatUserViewModel

class ChatUserFragment : BaseFragment<FragmentChatUserBinding, ChatUserViewModel>() {

    private var pick = false
    private var filterUserList: List<String>? = null
    private val REQUEST_CODE_PICK_CHANNEL = 100
    protected var userListAdapter: UserListAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    protected lateinit var contactViewModel: ContactViewModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_chat_user
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.userAdapter = ChatUserAdapter(this.context)
        binding.click = ClickProxy()
        contactViewModel = getFragmentScopeViewModel(ContactViewModel::class.java)
        val bundle = arguments
        if (bundle != null) {
            pick = bundle.getBoolean("pick", false)
            filterUserList = bundle.getStringArrayList("filterUserList")
        }
        afterViews(view)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userListAdapter != null && isVisibleToUser) {
            contactViewModel.reloadContact()
            contactViewModel.reloadFriendRequestStatus()
        }
    }

    override fun onResume() {
        super.onResume()
        contactViewModel.reloadContact()
        contactViewModel.reloadFriendRequestStatus()
        contactViewModel.reloadFavContact()
    }


    @SuppressLint("NewApi")
    protected fun afterViews(view: View?) {
        contactViewModel.contactListLiveData().observe(this.viewLifecycleOwner,
            Observer { userInfos: MutableList<UIUserInfo> ->
                if (filterUserList != null) {
                    userInfos.removeIf { uiUserInfo -> filterUserList!!.indexOf(uiUserInfo.getUserInfo().uid) > -1 }
                }
                userListAdapter?.setUsers(userInfos)
            })
        contactViewModel.favContactListLiveData().observe(this.viewLifecycleOwner,
            Observer { uiUserInfos: MutableList<UIUserInfo> ->
                if (filterUserList != null) {
                    uiUserInfos.removeIf { uiUserInfo -> filterUserList!!.indexOf(uiUserInfo.getUserInfo().uid) > -1 }
                }
                userListAdapter?.setFavUsers(uiUserInfos)
            })

        userListAdapter = onCreateUserListAdapter()
        userListAdapter!!.setOnUserClickListener(onUserClickListener)

        binding.usersRecyclerView.setAdapter(userListAdapter)
        linearLayoutManager = LinearLayoutManager(activity)
        binding.usersRecyclerView.setLayoutManager(linearLayoutManager)
    }

    private val onUserClickListener = UserListAdapter.OnUserClickListener{ userInfo->
        mViewModel.currentUserInfo.value = userInfo
    }

    /**
     * @return
     */
    protected fun onCreateUserListAdapter(): UserListAdapter? {
        userListAdapter = UserListAdapter(this)
        return userListAdapter
    }

    inner class ClickProxy{
        fun callPhone(){
            WfcUIKit.singleCall(getActivity(), mViewModel.currentUserInfo.value!!.userInfo.uid, true);
        }

        fun sendMsg(){

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PICK_CHANNEL && resultCode == Activity.RESULT_OK) {
            val intent = Intent()
            val channelInfo: ChannelInfo = data!!.getParcelableExtra("channelInfo")!!
            intent.putExtra("channelInfo", channelInfo)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}