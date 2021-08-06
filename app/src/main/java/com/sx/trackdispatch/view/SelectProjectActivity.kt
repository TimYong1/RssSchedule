package com.sx.trackdispatch.view

import android.content.Intent
import android.text.TextUtils
import cn.wildfire.chat.kit.ChatManagerHolder
import com.example.common.utils.DeviceInfoUtil
import com.example.common.utils.LogUtil
import com.sx.base.BaseActivity
import com.sx.base.net.HttpBaseObserver
import com.sx.base.net.HttpUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.adapter.DialogListAdapter
import com.sx.trackdispatch.api.Api
import com.sx.trackdispatch.databinding.ActivitySelectProjectBinding
import com.sx.trackdispatch.dialog.ListDialog
import com.sx.trackdispatch.dialog.LoadingDialog
import com.sx.trackdispatch.model.DeviceInfo
import com.sx.trackdispatch.model.ProjectBean
import com.sx.trackdispatch.model.ResponseBaseData
import com.sx.trackdispatch.utils.AppData
import com.sx.trackdispatch.viewmodel.APPViewModel
import com.sx.trackdispatch.viewmodel.SelectProjectViewModel
import com.util.toast.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.FormBody
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*
import kotlin.collections.ArrayList

class SelectProjectActivity: BaseActivity<ActivitySelectProjectBinding, SelectProjectViewModel>() {
    private lateinit var listDialog: ListDialog
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var api:Api

//    private val isClient = true

    override fun init() {
        binding.vm = mViewModel
        binding.click = ClickProxy()
        listDialog = ListDialog(this)
        initListener()
        api = HttpUtil.getRetrofitNoHeader().create(Api::class.java)
        loadingDialog = LoadingDialog.Builder().create(this)
//        LogUtil.e("本机 ip:${localIPAddress}")
//        if(isClient){
//            ConnectManage.instance.connect(Constants.CHAT_IP,Constants.CHAT_PORT,object :ConnectListener{
//                override fun onLine() {
//                    LogUtil.e("onLine")
//                }
//
//                override fun offLine() {
//                    LogUtil.e("offLine")
//                }
//            })
//        }else{
//            ConnectManage.instance.bindPort()
//        }

        addOrUpdate()
    }

    private fun initListener(){
        listDialog.setItemListener(object : DialogListAdapter.OnItemClickListener{
            override fun itemClick(position: Int,bean:ProjectBean) {
                mViewModel.currentProject.value = bean
                listDialog.dismiss()
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_select_project
    }

    private fun addOrUpdate(){
        loadingDialog.show()
        val body = FormBody.Builder()
        body.add("status", "normal")
//        body.add("longitude", "0")
//        body.add("latitude", "0")
        body.add("position", "1")
        body.add("ip", localIPAddress)
        body.add("locateMode", "GPS")
        body.add("mac", DeviceInfoUtil.instance.getDeviceId())
        api.addOrUpdate(body.build()).subscribeOn(Schedulers.io()) // 上面 异步
            .observeOn(AndroidSchedulers.mainThread()) // 下面 主线程
            .subscribe (object : HttpBaseObserver<ResponseBaseData<DeviceInfo>>(){
                override fun succeed(t: ResponseBaseData<DeviceInfo>?) {
                    LogUtil.d("网络请求返回了"+t.toString())
                    loadingDialog.dismiss()
                    if(t?.code==200){
                        if(t.data?.projectId!="0"){
                            AppData.currentProjectId = t.data?.projectId!!
                            startActivity(Intent(this@SelectProjectActivity,LoginActivity::class.java))
                            return
                        }
                        getProjectList()
                    }else{
                        ToastUtils.show(t?.msg)
                    }
                }

                override fun failed(e: Throwable?) {
                    LogUtil.d("网络请求失败：${e?.message}")
                    ToastUtils.show(e?.message)
                    loadingDialog.dismiss()
                }
            })
    }

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
                    }
                }

                override fun failed(e: Throwable?) {
                    LogUtil.d("网络请求失败：${e?.message}")
                    ToastUtils.show(e?.message)
                    loadingDialog.dismiss()
                }
            })
    }

    private fun bindProject(){
        api.trainMasterAddProject(DeviceInfoUtil.instance.getDeviceId(),mViewModel.currentProject.value!!.id).subscribeOn(Schedulers.io()) // 上面 异步
            .observeOn(AndroidSchedulers.mainThread()) // 下面 主线程
            .subscribe (object : HttpBaseObserver<ResponseBaseData<String>>(){
                override fun succeed(t: ResponseBaseData<String>?) {
                    LogUtil.d("网络请求返回了"+t.toString())
                    loadingDialog.dismiss()
                    if(t?.code==200){
                        AppData.currentProjectId =  mViewModel.currentProject.value!!.id
                        startActivity(Intent(this@SelectProjectActivity,LoginActivity::class.java))
                        finish()
                    }else{
                        ToastUtils.show(t?.msg)
                    }
                }

                override fun failed(e: Throwable?) {
                    LogUtil.d("网络请求失败：${e?.message}")
                    ToastUtils.show(e?.message)
                    loadingDialog.dismiss()
                }
            })
    }

    inner class ClickProxy {
        fun goMainActivity() {
            if(mViewModel.currentProject.value==null){
                ToastUtils.show("请选择项目")
                return
            }
            bindProject()

//            val test =  JSONObject.parseObject("{text:1111}")
//            if(isClient){
//                RequestManage.instance.request(RequestManage.Type.HOST,test,object :RequestCall{
//                    override fun success(content: String?) {
//                        LogUtil.e("发送成功: ${content}")
//                    }
//
//                    override fun fail(msg: String?) {
//                        LogUtil.e("发送失败: ${msg}")
//                    }
//                })
//            }else{
//                RequestManage.instance.request(RequestManage.Type.SERVICE,test,object :RequestCall{
//                    override fun success(content: String?) {
//                        LogUtil.e("发送成功: ${content}")
//                    }
//
//                    override fun fail(msg: String?) {
//                        LogUtil.e("发送失败: ${msg}")
//                    }
//                })
//            }

        }

        fun selectProject(){
            listDialog.showDialog(mViewModel.projectList.value!!)
        }
    }

    /**
     * 获取内网ip
     */
    val localIPAddress: String
        get() {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf: NetworkInterface = en.nextElement()
                val enumIpAddr: Enumeration<InetAddress> = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.hostAddress.toString()
                    }
                }
            }
            return "null"
        }

}