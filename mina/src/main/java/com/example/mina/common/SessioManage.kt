package com.example.mina.common

import android.os.Handler
import android.os.Looper
import android.os.Message
import org.apache.mina.core.session.IoSession
import java.util.concurrent.ConcurrentHashMap

class SessioManage private constructor() {
    var clientSession:IoSession?= null
    var hostSession:IoSession?= null
    var serviceSession:ConcurrentHashMap<String,IoSession>?= null
    private var callBacks = ConcurrentHashMap<String, RequestCall>()

    var timeOutHandler = object :Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            try {
                val key = msg.obj as String
                if (callBacks.containsKey(key)) {
                    callBacks.get(key)?.fail("发送超时")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    init {
        serviceSession = ConcurrentHashMap()
    }

    companion object {
        val instance: SessioManage by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SessioManage()
        }
    }

    fun removeCallBack(key: String){
        callBacks.remove(key)
    }

    fun getCallBack(key: String):RequestCall?{
        return callBacks.get(key)
    }

    fun addCallBack(key:String,call:RequestCall){
        callBacks.put(key,call)
    }
}