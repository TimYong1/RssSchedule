package com.sx.base

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.util.toast.ToastInterceptor
import com.util.toast.ToastUtils
import com.util.toast.style.ToastWhiteStyle


open class BaseApplication : Application() {

    companion object {
        var mainThreadId: Long = 0//主线程id
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        mainThreadId = android.os.Process.myTid().toLong()

        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(object : ToastInterceptor() {
            override fun intercept(toast: Toast, text: CharSequence?): Boolean {
                val intercept = super.intercept(toast, text)
                if (intercept) {
                    Log.e("Toast", "空 Toast")
                }
                return intercept
            }
        })
        // 初始化吐司工具类
        ToastUtils.init(this, ToastWhiteStyle(this))
    }
}