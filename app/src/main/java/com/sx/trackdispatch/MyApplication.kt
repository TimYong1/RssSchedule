package com.sx.trackdispatch

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import cn.wildfire.chat.kit.Config
import cn.wildfire.chat.kit.WfcUIKit
import com.example.common.utils.MMKVUtil
import com.example.common.utils.SPUtil
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechUtility
import com.sx.base.BaseApplication
import com.util.toast.ToastUtils
import java.io.File

class MyApplication : BaseApplication() {

    companion object{
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        MMKVUtil.initMMKV(this)
        AppService.validateConfig(this)
        SPUtil.initSp(this)
        ToastUtils.init(this)
        // 只在主进程初始化，否则会导致重复收到消息
        if (getCurProcessName(this) == BuildConfig.APPLICATION_ID) {
            val wfcUIKit = WfcUIKit.getWfcUIKit()
            wfcUIKit.init(this)
            wfcUIKit.appServiceProvider = AppService.Instance()
            setupWFCDirs()
        }
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(ApplicationObserver())
        // 应用程序入口处调用,避免手机内存过小,杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用“,”分隔。
        // 设置你申请的应用appid
        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
        //讯飞语音初始化
        val param = StringBuffer()
        param.append("appid=bb666b73")
        param.append(",")
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC)
        SpeechUtility.createUtility(this, param.toString())
    }

    private fun setupWFCDirs() {
        var file = File(Config.VIDEO_SAVE_DIR)
        if (!file.exists()) {
            file.mkdirs()
        }
        file = File(Config.AUDIO_SAVE_DIR)
        if (!file.exists()) {
            file.mkdirs()
        }
        file = File(Config.FILE_SAVE_DIR)
        if (!file.exists()) {
            file.mkdirs()
        }
        file = File(Config.PHOTO_SAVE_DIR)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    fun getCurProcessName(context: Context): String? {
        val pid = Process.myPid()
        val activityManager = context
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in activityManager
            .runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }
}
