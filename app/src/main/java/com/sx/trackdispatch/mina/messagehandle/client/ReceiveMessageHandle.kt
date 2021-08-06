package com.sx.armslockermvvm.mina.messagehandle.client

import com.alibaba.fastjson.JSONObject
import com.sx.armslockermvvm.mina.messagehandle.IMessageHandlerStrategy
import org.apache.mina.core.session.IoSession

class ReceiveMessageHandle : IMessageHandlerStrategy {
    override fun handleReceipt(session: IoSession, jsonObject: JSONObject) {

    }
}