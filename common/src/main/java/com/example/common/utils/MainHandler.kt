package com.example.common.utils

import android.os.Handler
import android.os.Looper

class MainHandler private constructor() {

    private var handler:Handler

    companion object{
        val instance: MainHandler by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { MainHandler() }
    }

    init {
        handler = Handler(Looper.getMainLooper())
    }

    fun getHandler():Handler{
        return handler
    }
}