package com.sx.armslockermvvm.mina.messagehandle.service

import com.alibaba.fastjson.JSONObject
import com.armslocker.util.MessageConst
import com.example.common.utils.DeviceInfoUtil
import com.example.mina.common.SessioManage
import com.sx.armslockermvvm.mina.messagehandle.IMessageHandlerStrategy
import com.sx.armslockermvvm.mina.request.RequestManage
import org.apache.mina.core.session.IoSession
import java.util.*

class ReceiveMessageHandle : IMessageHandlerStrategy {
    override fun handleReceipt(session: IoSession, jsonObject: JSONObject) {
        session.write(defaultReceiveContent(jsonObject))
    }

    fun defaultReceiveContent(jsonObject: JSONObject): JSONObject {
        val json = JSONObject()
        json[MessageConst.MESSAGETYPE] = MessageConst.RECEIVESERVERCLIENT
        json[MessageConst.PADKEY] = DeviceInfoUtil.instance.getDeviceId()
        json[MessageConst.MESSAGEID] = jsonObject.getString(MessageConst.MESSAGEID)
        return json
    }
}