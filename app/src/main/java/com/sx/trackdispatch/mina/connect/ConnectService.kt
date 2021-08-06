package com.sx.trackdispatch.mina.connect

import com.sx.armslockermvvm.mina.messagehandle.service.ServiceIoHandlerAdapter
import com.sx.armslockermvvm.mina.messagehandle.service.ServiceListener
import com.sx.trackdispatch.mina.messagehandle.KeepAliveServiceMsgFactory

import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.service.IoServiceListener
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory


class ConnectService private constructor() : BaseConnect() {

    override fun getIoHandlerAdapter(): IoHandlerAdapter {
        return ServiceIoHandlerAdapter()
    }

    override fun getKeepAliveFilter(): KeepAliveMessageFactory {
        return KeepAliveServiceMsgFactory()
    }

    override fun getIoServiceListener(listener: ConnectListener): IoServiceListener {
        return ServiceListener(listener)
    }

    override fun sessionClosed() {
        instance = null
    }

    companion object {
//        var instance: ConnectService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ConnectService() }
        @Volatile private var instance: ConnectService? = null
        fun getInstance() =instance ?: synchronized(this) {
                instance ?: ConnectService().also { instance = it }
            }
    }
}