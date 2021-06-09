package com.sx.trackdispatch.view

import android.os.Bundle
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.FragmentGroupsBinding
import com.sx.trackdispatch.viewmodel.GroupsViewModel

class GroupsFragment: BaseFragment<FragmentGroupsBinding, GroupsViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_groups
    }

    override fun init(savedInstanceState: Bundle?) {

    }
}