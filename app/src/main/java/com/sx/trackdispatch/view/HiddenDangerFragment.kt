package com.sx.trackdispatch.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sx.base.BaseFragment
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.HiddenDangerAdapter
import com.sx.trackdispatch.databinding.FragmentHiddenDangerBinding
import com.sx.trackdispatch.viewmodel.HiddenDangerViewModel

class HiddenDangerFragment : BaseFragment<FragmentHiddenDangerBinding, HiddenDangerViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_hidden_danger
    }

    override fun init(savedInstanceState: Bundle?) {
        binding.vm = mViewModel
        binding.adapter = HiddenDangerAdapter(this.context)
        binding.settingClick = settingClick
    }

    private val settingClick = View.OnClickListener { startActivity(Intent(this@HiddenDangerFragment.activity,SettingActivity::class.java)) }
}