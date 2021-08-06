package com.sx.armslockermvvm.mina.messagehandle.service

import com.example.common.utils.LogUtil
import com.example.common.utils.MainHandler
import com.sx.trackdispatch.mina.connect.ConnectListener

import org.apache.mina.core.service.IoService
import org.apache.mina.core.service.IoServiceListener
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession


class ServiceListener: IoServiceListener {
    private var listener: ConnectListener

    constructor(listener: ConnectListener){
        this.listener = listener
    }

    @Throws(Exception::class)
    override fun serviceActivated(arg0: IoService?) {
        LogUtil.d("serviceActivated")
    }

    @Throws(Exception::class)
    override fun serviceDeactivated(arg0: IoService?) {
        LogUtil.d("serviceDeactivated")
        MainHandler.instance.getHandler().post {
            listener.offLine()
        }
    }

    @Throws(Exception::class)
    override fun serviceIdle(arg0: IoService?, arg1: IdleStatus?) {
        LogUtil.d("serviceIdle")

    }

    @Throws(Exception::class)
    override fun sessionClosed(arg0: IoSession?) {
        LogUtil.d("sessionClosed")
    }

    @Throws(Exception::class)
    override fun sessionCreated(arg0: IoSession?) {
        LogUtil.d("sessionCreated")
    }

    override fun sessionDestroyed(arg0: IoSession) {
        LogUtil.d("sessionDestroyed")
        arg0.closeNow()
        arg0.close()
        try {
            arg0.filterChain.clear()
            arg0.closeFuture.setClosed()
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        EventBus.getDefault().post(EventbusMsgBean<String>(MessageConst.ONLINECHANGE, MessageConst.OFFLINE))
//        ConnectHostPad.instance.connect()
    }
}