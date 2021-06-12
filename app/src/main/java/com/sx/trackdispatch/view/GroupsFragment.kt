package com.sx.trackdispatch.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.FragmentGroupsBinding
import com.sx.trackdispatch.viewmodel.GroupsViewModel

class GroupsFragment: BaseFragment<FragmentGroupsBinding, GroupsViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_groups
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.settingClick = settingClick
    }

    private val settingClick = View.OnClickListener { startActivity(Intent(this.activity,SettingActivity::class.java)) }
}