package com.sx.trackdispatch.mina.connect

import com.example.common.utils.LogUtil
import com.sx.trackdispatch.mina.bindsocket.BindSocketPort
import java.util.concurrent.ConcurrentHashMap

class ConnectManage{
//    var clientList = ConcurrentHashMap<String,String>()
    private var isHost = true

    companion object{
        val instance: ConnectManage by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ConnectManage() }
    }

    private constructor(){
        LogUtil.e("创建ConnectManage")
    }

    fun sendMessage(){
        if(isHost){

        }else{

        }
    }

    fun bindPort(){
        isHost = true
        BindSocketPort.getInstance().bindPort()
    }

    fun connect(ip:String,port:Int,listener: ConnectListener){
        isHost = false
        ConnectClient.instance.connect(ip,port,listener)
    }
}