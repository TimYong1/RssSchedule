package com.sx.armslockermvvm.mina.messagehandle

import com.alibaba.fastjson.JSONObject
import org.apache.mina.core.session.IoSession

/**
 * 处理后台服务心跳消息
 */
class HeartbeatHandler :IMessageHandlerStrategy {
    override fun handleReceipt(session: IoSession, jsonObject: JSONObject) {
        session.write(0)
    }
}