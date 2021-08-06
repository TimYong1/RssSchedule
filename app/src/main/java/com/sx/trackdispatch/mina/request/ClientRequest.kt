package com.sx.armslockermvvm.mina.request

import com.alibaba.fastjson.JSONObject
import com.armslocker.util.MessageConst
import com.example.common.utils.DeviceInfoUtil
import com.example.mina.common.BaseRequest
import com.example.mina.common.SessioManage
import org.apache.mina.core.session.IoSession
import java.util.concurrent.ConcurrentHashMap

class ClientRequest : BaseRequest() {
    val TAG = "ClientRequest发送消息"

    override fun getContent(obj:JSONObject): JSONObject {
        val json = JSONObject()
        json[MessageConst.MESSAGETYPE] = getType(obj)
        json[MessageConst.PADKEY] = DeviceInfoUtil.instance.getDeviceId()
        json[MessageConst.MESSAGEID] = getMsgId(obj)
        json[MessageConst.MSGDATA] = obj.get(MessageConst.MSGDATA)
        return json
    }

    private fun getType(obj:JSONObject):String{
        var type = ""
        if(obj.containsKey(MessageConst.MESSAGETYPE)){
            type = obj.getString(MessageConst.MESSAGETYPE)
        }
        return type
    }

    override fun init(obj:JSONObject) {}

    override fun getTag(): String {
        return TAG
    }

    override fun getSession(): ConcurrentHashMap<String, IoSession> {
        val list = ConcurrentHashMap<String,IoSession>()
        list.put("client",SessioManage.instance.clientSession!!)
        return list
    }
}