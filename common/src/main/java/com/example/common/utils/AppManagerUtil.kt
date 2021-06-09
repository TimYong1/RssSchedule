package com.example.common.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.common.R
import java.util.*
import kotlin.system.exitProcess

class AppManagerUtil private constructor() {

    /**
     * The Activity is added to the stack
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    /**
     * Gets the current Activity (stack last pressed into)
     */
    fun currentActivity(): Activity? {
        return if (ObjectUtils.isEmpty(activityStack)) {
            null
        } else activityStack!!.lastElement()
    }

    /**
     * The last of the finish of the current Activity (stack into)
     */
//    fun finishActivity() {
//        if (ObjectUtils.isEmpty(activityStack)) {
//            return
//        }
//        val activity = activityStack!!.lastElement()
//        finishActivity(activity)
//    }

    /**
     * The finish of the specified Activity
     */
    fun finishActivity(activity: Activity?) {
        var tempActivity = activity
        if (ObjectUtils.isEmpty(activityStack)) {
            return
        }
        if (tempActivity != null) {
            if(activityStack?.size==1){
                Log.d("测试","-------------------异常调用退出--------------------")
                return
            }
            activityStack!!.remove(tempActivity)
            tempActivity.finish()
            tempActivity.overridePendingTransition(
                R.anim.tran_pre_in,
                R.anim.tran_pre_out
            )
            tempActivity = null
        }
    }

    fun removeActivity(activity: Activity?){
        var tempActivity = activity
        if (ObjectUtils.isEmpty(activityStack)) {
            return
        }
        if (tempActivity != null) {
            activityStack!!.remove(tempActivity)
        }
    }

    /**
     * End of the specified class name of the Activity
     */
    fun finishActivity(cls: Class<*>) {
        if (ObjectUtils.isEmpty(activityStack)) {
            return
        }
        for (activity in activityStack!!) {
            if (activity == null) {
                return
            }
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * Finish all the Activity
     */
    fun finishAllActivity() {
        LogUtil.d("finishAllActivity() called with: ")

        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                LogUtil.d("activity" + activityStack!![i].javaClass.simpleName)

                activityStack!![i].finish()
            }
            i++
        }
        activityStack!!.clear()
    }

    /**
     * Finish all except login
     */
    fun finishAllExceptLogin(exceptActivity: Activity) {
        for (i in activityStack!!.indices) {
            if (null != activityStack!![i]) {
                if (activityStack!![i] !== exceptActivity) {
                    finishActivity(activityStack!![i])
                }
            }
        }
    }

    /**
     * Exit the application
     */
    fun AppExit() {
        LogUtil.d("AppExit() called with: ")
        finishAllActivity()
        //        MobclickAgent.onKillProcess(context);
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(1)
    }

    companion object {

        private var activityStack: Stack<Activity>? = null
        private var instance: AppManagerUtil? = null

        /**
         * A single instance
         */
        fun instance(): AppManagerUtil {
            if (instance == null) {
                synchronized(AppManagerUtil::class.java) {
                    if (instance == null) {
                        instance = AppManagerUtil()
                    }
                }
            }
            return instance!!
        }
    }
}