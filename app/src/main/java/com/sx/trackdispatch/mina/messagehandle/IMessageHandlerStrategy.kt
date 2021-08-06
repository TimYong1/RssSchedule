package com.sx.armslockermvvm.mina.messagehandle

import com.alibaba.fastjson.JSONObject
import org.apache.mina.core.session.IoSession

interface IMessageHandlerStrategy {
    fun handleReceipt(session: IoSession,jsonObject: JSONObject)
}