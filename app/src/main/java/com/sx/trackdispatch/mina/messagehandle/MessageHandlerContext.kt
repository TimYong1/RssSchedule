package com.sx.armslockermvvm.mina.messagehandle

import com.alibaba.fastjson.JSONObject
import org.apache.mina.core.session.IoSession

class MessageHandlerContext<E> {
    private var factory: HandlerFactory<E>? = null

    constructor(factory: HandlerFactory<E>){
        this.factory = factory
    }

    fun handlerMessage(key:E,session: IoSession,jsonObject: JSONObject){
        factory?.get(key)?.handleReceipt(session,jsonObject)
    }
}