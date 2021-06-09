package com.sx.trackdispatch

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.ezvizuikit.open.EZUIKit
import com.sx.base.AppConfig
import com.sx.base.BaseApplication
import com.videogo.openapi.EZOpenSDK

class MyApplication : BaseApplication() {

    companion object{
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
//        RongIM.init(this,AppConfig.IMKey)
        /** * sdk日志开关，正式发布需要去掉 */
        EZOpenSDK.showSDKLog(true)
        /** * 设置是否支持P2P取流,详见api */
        EZOpenSDK.enableP2P(false)
        /** * APP_KEY请替换成自己申请的 */
        EZUIKit.initWithAppKey(this, AppConfig.YSAPPKey)
        ProcessLifecycleOwner.get().getLifecycle().addObserver(ApplicationObserver())
    }
}
