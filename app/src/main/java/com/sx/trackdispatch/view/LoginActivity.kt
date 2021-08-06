package com.sx.trackdispatch.view

import android.content.Context
import android.content.Intent
import cn.wildfire.chat.kit.ChatManagerHolder
import com.example.common.utils.LogUtil
import com.example.common.utils.SPUtil
import com.sx.base.BaseActivity
import com.sx.base.net.HttpBaseObserver
import com.sx.base.net.HttpUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.api.Api
import com.sx.trackdispatch.databinding.ActivityLoginBinding
import com.sx.trackdispatch.dialog.ListDialog
import com.sx.trackdispatch.dialog.LoadingDialog
import com.sx.trackdispatch.model.IMToken
import com.sx.trackdispatch.model.LoginToken
import com.sx.trackdispatch.model.ResponseBaseData
import com.sx.trackdispatch.viewmodel.LoginViewModel
import com.sx.trackdispatch.viewmodel.SelectProjectViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.FormBody
import java.util.*

class LoginActivity: BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    private lateinit var listDialog: ListDialog
    private lateinit var api:Api
    private lateinit var loadingDialog: LoadingDialog

    override fun init() {
        binding.vm = mViewModel
        binding.click = ClickProxy()
        listDialog = ListDialog(this)
        initListener()
        api = HttpUtil.getRetrofit().create(Api::class.java)
        loadingDialog = LoadingDialog.Builder().create(this)
    }

    private fun initListener(){

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    fun loginSms() {
        loadingDialog.show()
        val body = FormBody.Builder()
        body.add("grant_type", "password")
        body.add("scope", "web")
        body.add("client_id", "webApp")
        body.add("client_secret", "webApp")
        body.add("username", binding.edUserId.text.toString())
        body.add("password", binding.edPassword.text.toString())
        body.add("platform", "2")
        body.add("clientId", ChatManagerHolder.gChatManager.getClientId())
        api.getToken(body.build()).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread()) // 下面 主线程
            .subscribe(object : HttpBaseObserver<ResponseBaseData<LoginToken>>() {
                override fun succeed(t: ResponseBaseData<LoginToken>?) {
                    LogUtil.d("网络请求返回了" + t?.code + "  " + t?.msg)
                    if (t?.code == 200) {
                        try {
                            SPUtil.putToken(t.data?.token!!)
                            SPUtil.putUserId(binding.edUserId.text.toString())
                            val imBody = FormBody.Builder()
                            imBody.add("clientId", ChatManagerHolder.gChatManager.getClientId())
                            imBody.add("platform", "2")
                            api.getIMToken(imBody.build())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()) // 下面 主线程
                                .subscribe(object : HttpBaseObserver<IMToken>() {
                                    override fun succeed(t: IMToken?) {
                                        loadingDialog.dismiss()
                                        if (t?.code==0) {
                                            SPUtil.putIMToken(t.result!!.token)
                                            SPUtil.putIMUserId(t.result!!.userId)
//                                            需要注意token跟clientId是强依赖的，一定要调用getClientId获取到clientId，然后用这个clientId获取token，这样connect才能成功，如果随便使用一个clientId获取到的token将无法链接成功。
                                            ChatManagerHolder.gChatManager.connect(t.result!!.userId, t.result!!.token)
                                            val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
                                            sp.edit().putString("id", t.result!!.userId)
                                                .putString("token", t.result!!.token)
                                                .apply()
                                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(intent)
//                                            finish()
                                        }else{
                                            showToast(t?.message)
                                        }
                                    }

                                    override fun failed(e: Throwable?) {
                                        showToast(e?.message)
                                        loadingDialog.dismiss()
                                    }
                                })
                        } catch (e: Exception) {
                            e.printStackTrace()
                            showToast(e.message)
                            loadingDialog.dismiss()
                        }
                    } else {
                        showToast("登录失败${t?.msg}")
                        loadingDialog.dismiss()
                    }
                }

                override fun failed(e: Throwable?) {
                    LogUtil.d("网络请求失败：${e?.message}")
                    loadingDialog.dismiss()
                }
            })
    }

    private fun getIMTkeon(){
        loadingDialog.show()
        SPUtil.putUserId(binding.edUserId.text.toString())
        val imBody = FormBody.Builder()
        imBody.add("clientId", ChatManagerHolder.gChatManager.getClientId())
        imBody.add("platform", "2")
        api.getIMToken(imBody.build())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) // 下面 主线程
            .subscribe(object : HttpBaseObserver<IMToken>() {
                override fun succeed(t: IMToken?) {
                    loadingDialog.dismiss()
                    if (t?.code==0) {
                        SPUtil.putIMToken(t.result!!.token)
                        SPUtil.putIMUserId(t.result!!.userId)
//                                            需要注意token跟clientId是强依赖的，一定要调用getClientId获取到clientId，然后用这个clientId获取token，这样connect才能成功，如果随便使用一个clientId获取到的token将无法链接成功。
                        ChatManagerHolder.gChatManager.connect(t.result!!.userId, t.result!!.token)
                        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
                        sp.edit().putString("id", t.result!!.userId)
                            .putString("token", t.result!!.token)
                            .apply()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }else{
                        showToast(t?.message!!)
                    }
                }

                override fun failed(e: Throwable?) {
                    showToast(e?.message!!)
                    loadingDialog.dismiss()
                }
            })
    }

    inner class ClickProxy {
        fun login(){
//            getIMTkeon()
            loginSms()
        }
    }
}