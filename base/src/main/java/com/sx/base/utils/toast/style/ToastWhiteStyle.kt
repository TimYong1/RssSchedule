package com.util.toast.style

import android.content.Context

/**
 * desc   : 默认白色样式实现
 */
class ToastWhiteStyle(context: Context) : ToastBlackStyle(context) {

    override val yOffset: Int
        get() = 240

    override val backgroundColor: Int
        get() = -0x151516

    override val textColor: Int
        get() = -0x45000000

    override val paddingStart: Int
        get() = dp2px(16f)

    override val paddingTop: Int
        get() = dp2px(12f)
}