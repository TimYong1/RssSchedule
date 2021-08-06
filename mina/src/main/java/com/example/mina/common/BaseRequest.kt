package com.example.mina.common

import android.os.Message
import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.armslocker.util.BaseConfig
import com.armslocker.util.MessageConst
import org.apache.mina.core.session.IoSession
import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class BaseRequest :IRequest {

    override fun request(content: JSONObject, call: RequestCall) {
        try {
            init(content)
            if (content.isEmpty()) {
                call.fail("发送消息不能为空")
                return
            }
            if (getSession() != null) {
                val json = getContent(content).toJSONString()
                val msgId = getMsgId(content)
                getSession()?.forEach {
                    it.value.write(json)
                }
//                getSession()?.write(json)
                Log.e(getTag(), json)
                SessioManage.instance.timeOutHandler.sendMessageDelayed(
                    getMsg(msgId),
                    BaseConfig.minaTimeOut
                )
                SessioManage.instance.addCallBack(msgId, call)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            call.fail(e.message)
        }
    }

    fun getMsgId(content: JSONObject):String{
        var msgId = ""
        if(content.containsKey(MessageConst.MESSAGEID)){
            msgId = content.getString(MessageConst.MESSAGEID)
        }else{
            msgId = UUID.randomUUID().toString()
        }
        return msgId
    }

    fun getMsg(msgId:String): Message?{
        val msg = SessioManage.instance.timeOutHandler.obtainMessage()
        msg.obj = msgId
        return msg
    }

    abstract fun getContent(obj:JSONObject):JSONObject

    abstract fun init(obj:JSONObject)

    abstract fun getTag():String

    abstract fun getSession():ConcurrentHashMap<String,IoSession> ?
}