package com.sx.trackdispatch.view

import android.view.View
import com.sx.base.BaseActivity
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ActivitySettingBinding
import com.sx.trackdispatch.viewmodel.SettingViewModel

class SettingActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>() {

    override fun init() {
        binding.backClick = backClick
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    val backClick = View.OnClickListener { finish() }
}