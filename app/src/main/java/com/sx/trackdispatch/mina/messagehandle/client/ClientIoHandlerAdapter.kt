package com.sx.armslockermvvm.mina.messagehandle.client

import android.util.Log
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.armslocker.util.MessageConst
import com.example.mina.common.RequestCall
import com.example.mina.common.SessioManage
import com.sx.armslockermvvm.mina.messagehandle.MessageHandlerContext
import com.sx.armslockermvvm.mina.request.RequestManage
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession

/**
 * 丛机  -->  主机  接收主机消息
 */
class ClientIoHandlerAdapter : IoHandlerAdapter() {
    private val TAG: String = "ClientIoHandlerAdapter"
    var messageHandlerContext: MessageHandlerContext<Int>? = null

    @Throws(Exception::class)
    override fun exceptionCaught(session: IoSession?, cause: Throwable) {
        Log.d(TAG, "exceptionCaught: 异常信息$cause")
        if (session != null) {
            session.close(true)
            Log.d(TAG, "关闭连接")
        }
        Log.d(TAG, "客户端调用exceptionCaught")
    }

    override fun messageReceived(session: IoSession, message: Any) {
        Log.d(TAG, "接收到服务器端消息：$message")
        formatData(message.toString(), session)
    }

    private fun formatData(content: Any, session: IoSession) {
        try {
            val data = JSONObject.parseObject(content.toString())
            val type: Int = data.getIntValue(MessageConst.MESSAGETYPE)
            val id = data.getString(MessageConst.MESSAGEID)
            if(type==MessageConst.RECEIVESERVERCLIENT){
                SessioManage.instance.removeCallBack(id)
            }
            if (messageHandlerContext == null) {
                messageHandlerContext = MessageHandlerContext<Int>(MessageHandlerFactory())
            }
            messageHandlerContext?.handlerMessage(type,session,data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class)
    override fun inputClosed(ioSession: IoSession?) {
        super.inputClosed(ioSession)
    }

    @Throws(Exception::class)
    override fun messageSent(session: IoSession?, message: Any?) {
        super.messageSent(session, message)
        Log.d(TAG, " HeartBeatHandler: 客户端调用messageSent")
        //        session.close(true);//加上这句话实现短连接的效果，向客户端成功发送数据后断开连接
    }

    @Throws(Exception::class)
    override fun sessionClosed(session: IoSession?) {
        Log.d(TAG, "HeartBeatHandler: 客户端调用sessionClosed")
        super.sessionClosed(session)
    }

    @Throws(Exception::class)
    override fun sessionCreated(session: IoSession?) {
        Log.d(TAG, "HeartBeatHandler: 客户端调用sessionCreated")
        super.sessionCreated(session)
    }

    @Throws(Exception::class)
    override fun sessionIdle(session: IoSession, status: IdleStatus?) {
        super.sessionIdle(session, status)
        session.close(true)
        Log.d(TAG, "HeartBeatHandler: 客户端调用sessionIdle")
    }

    @Throws(Exception::class)
    override fun sessionOpened(session: IoSession?) {
        super.sessionOpened(session)
        Log.d(TAG, "HeartBeatHandler: 客户端调用sessionOpened")
        RequestManage.instance.request(RequestManage.Type.HOST, JSON.parseObject("{connect:'ok'}"),object :RequestCall{
            override fun success(content: String?) {

            }

            override fun fail(msg: String?) {

            }

        })
    }

}