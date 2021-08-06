package com.sx.armslockermvvm.mina.request

import com.alibaba.fastjson.JSONObject
import com.example.mina.common.IRequest
import com.example.mina.common.RequestCall

class RequestManage {
    var map = mutableMapOf<Type,IRequest>()

    private constructor(){
        map.put(Type.CLIENT,ClientRequest())
        map.put(Type.SERVICE,ServiceRequest())
        map.put(Type.HOST,HostRequest())
    }

    companion object {
        val instance: RequestManage by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RequestManage() }
    }

    fun request(type:Type,jsonObject: JSONObject,call: RequestCall){
        map.get(type)?.request(jsonObject,call)
    }

    enum class Type {
        CLIENT,SERVICE,HOST
    }
}