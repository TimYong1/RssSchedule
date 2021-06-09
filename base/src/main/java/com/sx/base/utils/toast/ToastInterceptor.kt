package com.util.toast

import android.widget.Toast

/**
 * desc   : Toast 默认拦截器
 */
open class ToastInterceptor : IToastInterceptor {

    override fun intercept(toast: Toast, text: CharSequence?): Boolean {
        return text == null || "" == text.toString()
    }
}