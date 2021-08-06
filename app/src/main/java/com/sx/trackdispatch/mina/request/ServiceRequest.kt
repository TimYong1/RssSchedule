package com.sx.armslockermvvm.mina.request

import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.armslocker.util.BaseConfig
import com.armslocker.util.MessageConst
import com.example.common.Constants
import com.example.common.utils.DeviceInfoUtil
import com.example.mina.common.BaseRequest
import com.example.mina.common.RequestCall
import com.example.mina.common.SessioManage
import org.apache.mina.core.session.IoSession
import java.util.concurrent.ConcurrentHashMap

class ServiceRequest : BaseRequest() {
    private var padKey = ""
    val TAG = "ServiceRequest发送消息"

    override fun getContent(obj: JSONObject): JSONObject {
        val json = JSONObject()
        json[MessageConst.MESSAGETYPE] = MessageConst.RECEIVESERVER
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

    override fun init(obj:JSONObject) {
        getPadKey(obj)
    }

    override fun getTag(): String {
        return TAG
    }

    fun getPadKey(content: JSONObject){
        if(content.containsKey(MessageConst.SENDPADKEY)){
            padKey = content.getString(MessageConst.SENDPADKEY)
        }
    }
    override fun getSession(): ConcurrentHashMap<String, IoSession>? {
        return SessioManage.instance.serviceSession
    }
}