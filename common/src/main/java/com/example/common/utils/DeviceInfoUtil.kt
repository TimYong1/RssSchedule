package com.example.common.utils

import android.os.Build

class DeviceInfoUtil private constructor() {

    companion object {
        val instance: DeviceInfoUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DeviceInfoUtil() }
    }

    fun getDeviceId():String{
        return Build.SERIAL
    }
}