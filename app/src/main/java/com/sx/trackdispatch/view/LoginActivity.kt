package com.sx.trackdispatch.view

import android.content.Intent
import com.example.common.utils.LogUtil
import com.sx.base.BaseActivity
import com.sx.base.net.HttpUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.DialogListAdapter
import com.sx.trackdispatch.api.Api
import com.sx.trackdispatch.databinding.ActivityLoginBinding
import com.sx.trackdispatch.dialog.ListDialog
import com.sx.trackdispatch.viewmodel.LoginViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginActivity: BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    private lateinit var listDialog: ListDialog
    private lateinit var api:Api

    override fun init() {
        binding.vm = mViewModel
        binding.click = ClickProxy()
        listDialog = ListDialog(this)
        initListener()
        api = HttpUtil.getRetrofit().create(Api::class.java)

//        api.getProjectItem(1, 294).subscribeOn(Schedulers.io()) // 上面 异步
//            .observeOn(AndroidSchedulers.mainThread()) // 下面 主线程
//            .subscribe {
//                LogUtil.d("网络请求返回了"+it.toString())
//            }
    }

    private fun initListener(){
        listDialog.setItemListener(object : DialogListAdapter.OnItemClickListener{
            override fun itemClick(position: Int,bean:String) {
                mViewModel.projectName.value = bean
                listDialog.dismiss()
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    inner class ClickProxy {
        fun goMainActivity() {
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }

        fun selectProject(){
            listDialog.showDialog(mViewModel.projectList.value!!)
        }
    }
}