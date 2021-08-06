package com.sx.armslockermvvm.mina.messagehandle.service

import com.alibaba.fastjson.JSONObject
import com.sx.armslockermvvm.mina.messagehandle.IMessageHandlerStrategy
import org.apache.mina.core.session.IoSession

class ReceiveMessageHandleHost : IMessageHandlerStrategy {
    override fun handleReceipt(session: IoSession, jsonObject: JSONObject) {

    }
}