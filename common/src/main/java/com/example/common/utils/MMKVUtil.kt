package com.example.common.utils

import android.content.Context
import com.tencent.mmkv.MMKV

object MMKVUtil {

    fun getMMKV():MMKV{
        return MMKV.defaultMMKV()
    }

    fun initMMKV(context: Context){
        MMKV.initialize(context)
    }
}