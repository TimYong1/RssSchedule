package com.sx.armslockermvvm.mina.request

import com.alibaba.fastjson.JSONObject
import com.armslocker.util.MessageConst
import com.example.common.utils.DeviceInfoUtil
import com.example.mina.common.BaseRequest
import com.example.mina.common.SessioManage
import org.apache.mina.core.session.IoSession
import java.util.concurrent.ConcurrentHashMap


class HostRequest : BaseRequest() {
    val TAG = "HostRequest发送消息"

    override fun getContent(obj: JSONObject): JSONObject {
        val json = JSONObject()
        json[MessageConst.MESSAGETYPE] = MessageConst.RECEIVESERVER
        json[MessageConst.PADKEY] = DeviceInfoUtil.instance.getDeviceId()
        json[MessageConst.MESSAGEID] = getMsgId(obj)
        json[MessageConst.MSGDATA] = obj.get(MessageConst.MSGDATA)
        return json
    }

    override fun init(obj: JSONObject) {
    }

    override fun getTag(): String {
        return TAG
    }

    override fun getSession(): ConcurrentHashMap<String,IoSession>? {
        val list = ConcurrentHashMap<String,IoSession>()
        list.put("host",SessioManage.instance.hostSession!!)
        return list
    }
}