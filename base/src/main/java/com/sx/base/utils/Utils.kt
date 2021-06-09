package com.sx.base.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import java.lang.reflect.InvocationTargetException
import java.util.*

class Utils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    internal class ActivityLifecycleImpl : ActivityLifecycleCallbacks {

        val mActivityList = LinkedList<Activity>()
        val mStatusListenerMap = HashMap<Any, OnAppStatusChangedListener>()

        private var mForegroundCount = 0
        private var mConfigCount = 0

        // using reflect to get top activity
        var topActivity: Activity?
            get() {
                if (!mActivityList.isEmpty()) {
                    val topActivity = mActivityList.last
                    if (topActivity != null) {
                        return topActivity
                    }
                }
                try {
                    @SuppressLint("PrivateApi")
                    val activityThreadClass = Class.forName("android.app.ActivityThread")
                    val activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null)
                    val activitiesField = activityThreadClass.getDeclaredField("mActivityList")
                    activitiesField.isAccessible = true
                    val activities = activitiesField.get(activityThread) as Map<*, *> ?: return null
                    for (activityRecord in activities.values) {
                        val activityRecordClass = activityRecord!!::class.java
                        val pausedField = activityRecordClass.getDeclaredField("paused")
                        pausedField.setAccessible(true)
                        if (!pausedField.getBoolean(activityRecord)) {
                            val activityField = activityRecordClass.getDeclaredField("activity")
                            activityField.setAccessible(true)
                            val activity = activityField.get(activityRecord) as Activity
                            topActivity = activity
                            return activity
                        }
                    }
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                }

                return null
            }
            private set(activity) {
                if (activity!!::class.java == PermissionUtils.PermissionActivity::class.java) return
                if (mActivityList.contains(activity)) {
                    if (mActivityList.last != activity) {
                        mActivityList.remove(activity)
                        mActivityList.addLast(activity)
                    }
                } else {
                    mActivityList.addLast(activity)
                }
            }

        fun addListener(`object`: Any, listener: OnAppStatusChangedListener) {
            mStatusListenerMap[`object`] = listener
        }

        fun removeListener(`object`: Any) {
            mStatusListenerMap.remove(`object`)
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            topActivity = activity
        }

        override fun onActivityStarted(activity: Activity) {
            topActivity = activity
            if (mForegroundCount <= 0) {
                postStatus(true)
            }
            if (mConfigCount < 0) {
                ++mConfigCount
            } else {
                ++mForegroundCount
            }
        }

        override fun onActivityResumed(activity: Activity) {
            topActivity = activity
        }

        override fun onActivityPaused(activity: Activity) {/**/
        }

        override fun onActivityStopped(activity: Activity) {
            if (activity.isChangingConfigurations) {
                --mConfigCount
            } else {
                --mForegroundCount
                if (mForegroundCount <= 0) {
                    postStatus(false)
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {/**/
        }

        override fun onActivityDestroyed(activity: Activity) {
            mActivityList.remove(activity)
        }

        private fun postStatus(isForeground: Boolean) {
            if (mStatusListenerMap.isEmpty()) return
            for (onAppStatusChangedListener in mStatusListenerMap.values) {
                if (onAppStatusChangedListener == null) return
                if (isForeground) {
                    onAppStatusChangedListener.onForeground()
                } else {
                    onAppStatusChangedListener.onBackground()
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    interface OnAppStatusChangedListener {
        fun onForeground()

        fun onBackground()
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var sApplication: Application? = null

        internal val activityLifecycle = ActivityLifecycleImpl()

        /**
         * Init utils.
         *
         * Init it in the class of Application.
         *
         * @param context context
         */
        fun init(context: Context) {
            init(context.applicationContext as Application)
        }

        /**
         * Init utils.
         *
         * Init it in the class of Application.
         *
         * @param app application
         */
        fun init(app: Application) {
            if (sApplication == null) {
                Utils.sApplication = app
                Utils.sApplication!!.registerActivityLifecycleCallbacks(activityLifecycle)
            }
        }

        /**
         * Return the context of Application object.
         *
         * @return the context of Application object
         */
        val app: Application?
            get() {
                if (sApplication != null) return sApplication
                try {
                    @SuppressLint("PrivateApi")
                    val activityThread = Class.forName("android.app.ActivityThread")
                    val at = activityThread.getMethod("currentActivityThread").invoke(null)
                    val app = activityThread.getMethod("getApplication").invoke(at)
                            ?: throw NullPointerException("u should init first")
                    init(app as Application)
                    return sApplication
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }

                throw NullPointerException("u should init first")
            }

        internal val activityList: LinkedList<Activity>
            get() = activityLifecycle.mActivityList

        internal val topActivityOrApp: Context?
            get() {
                if (isAppForeground) {
                    val topActivity = activityLifecycle.topActivity
                    return topActivity ?: Utils.app
                } else {
                    return Utils.app
                }
            }

        internal val isAppForeground: Boolean
            get() {
                val am = Utils.app!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val info = am.runningAppProcesses
                if (info == null || info.size == 0) return false
                for (aInfo in info) {
                    if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return aInfo.processName == Utils.app!!.packageName
                    }
                }
                return false
            }
    }
}
