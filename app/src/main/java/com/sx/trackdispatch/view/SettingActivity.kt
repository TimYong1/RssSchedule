package com.sx.trackdispatch.view

import android.content.Intent
import android.view.View
import cn.wildfire.chat.kit.ChatManagerHolder
import com.example.common.utils.DeviceInfoUtil
import com.example.common.utils.LogUtil
import com.sx.base.BaseActivity
import com.sx.base.net.HttpBaseObserver
import com.sx.base.net.HttpUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.DialogListAdapter
import com.sx.trackdispatch.api.Api
import com.sx.trackdispatch.databinding.ActivitySettingBinding
import com.sx.trackdispatch.dialog.DeviceInfoSynDialog
import com.sx.trackdispatch.dialog.ListDialog
import com.sx.trackdispatch.dialog.LoadingDialog
import com.sx.trackdispatch.model.ProjectBean
import com.sx.trackdispatch.model.ResponseBaseData
import com.sx.trackdispatch.utils.AppData
import com.sx.trackdispatch.viewmodel.APPViewModel
import com.sx.trackdispatch.viewmodel.SettingViewModel
import com.util.toast.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>() {
    lateinit var loadingDialog:LoadingDialog
    lateinit var deviceInfoSynDialog:DeviceInfoSynDialog
    private lateinit var listDialog: ListDialog
    private lateinit var api: Api

    override fun init() {
        binding.backClick = backClick
        binding.vm = mViewModel
        binding.click = ClickProcy()
        loadingDialog = LoadingDialog.Builder().create(this)
        deviceInfoSynDialog = DeviceInfoSynDialog.Builder.create(this)
        api = HttpUtil.getRetrofit().create(Api::class.java)
        listDialog = ListDialog(this)
        getProjectList()
        initListener()
    }

    private fun initListener() {
        listDialog.setItemListener(object : DialogListAdapter.OnItemClickListener{
            override fun itemClick(position: Int,bean:ProjectBean) {
                mViewModel.currentProject.value = bean
                listDialog.dismiss()
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    val backClick = View.OnClickListener { finish() }

    private fun getProjectList(){
        loadingDialog.show()
        api.getProject().subscribeOn(Schedulers.io()) // 上面 异步
            .observeOn(AndroidSchedulers.mainThread()) // 下面 主线程
            .subscribe (object : HttpBaseObserver<ResponseBaseData<ArrayList<ProjectBean>>>(){
                override fun succeed(t: ResponseBaseData<ArrayList<ProjectBean>>?) {
                    LogUtil.d("网络请求返回了"+t.toString())
                    loadingDialog.dismiss()
                    if(t?.code==200){
                        mViewModel.projectList.value = t.data!!
                        t.data!!.forEach {
                            if(it.id== AppData.currentProjectId){
                                mViewModel.currentProject.value = it
                            }
                        }
                        if(mViewModel.state.value!!){
                            listDialog.showDialog(mViewModel.projectList.value!!)
                        }
                    }
                }

                override fun failed(e: Throwable?) {
                    LogUtil.d("网络请求失败：${e?.message}")
                    ToastUtils.show(e?.message)
                    loadingDialog.dismiss()
                }
            })
    }

    private fun updateProject(){
        loadingDialog.show()
        api.updateProject(DeviceInfoUtil.instance.getDeviceId(),AppData.currentProjectId).subscribeOn(Schedulers.io()) // 上面 异步
            .observeOn(AndroidSchedulers.mainThread()) // 下面 主线程
            .subscribe (object : HttpBaseObserver<ResponseBaseData<String>>(){
                override fun succeed(t: ResponseBaseData<String>?) {
                    LogUtil.d("网络请求返回了"+t.toString())
                    loadingDialog.dismiss()
                    if(t?.code==200){
                        ToastUtils.show("修改成功")
                        AppData.currentProjectId = mViewModel.currentProject.value!!.id
                    }else{
                        ToastUtils.show("修改失败")
                    }
                }

                override fun failed(e: Throwable?) {
                    LogUtil.d("网络请求失败：${e?.message}")
                    ToastUtils.show(e?.message)
                    loadingDialog.dismiss()
                }
            })
    }

    inner class ClickProcy{
        fun changeState(){
            mViewModel.state.value = !mViewModel.state.value!!
            if(!mViewModel.state.value!!){
                updateProject()
            }
        }

        fun changeProject(){
            if(!mViewModel.state.value!!){
                return
            }
            if(mViewModel.projectList.value!!.size==0){
                getProjectList()
            }else{
                listDialog.showDialog(mViewModel.projectList.value!!)
            }
        }

        fun synInfo(){
            GlobalScope.launch(Dispatchers.Main) {
                deviceInfoSynDialog.show()
                delay(2000)
                deviceInfoSynDialog.dismiss()
            }
        }

        fun loginOut(){
            ChatManagerHolder.gChatManager.disconnect(true, false)
            startActivity(Intent(this@SettingActivity,LoginActivity::class.java))
            finish()
        }
    }
}