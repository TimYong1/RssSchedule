package com.sx.trackdispatch.mina.connect

import com.sx.armslockermvvm.mina.messagehandle.client.ClientIoHandlerAdapter
import com.sx.armslockermvvm.mina.messagehandle.client.ClientIoServiceListener
import com.sx.trackdispatch.mina.messagehandle.KeepAliveMsgFactory

import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.service.IoServiceListener
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory

class ConnectClient private constructor(): BaseConnect() {

    companion object{
        val instance: ConnectClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ConnectClient() }
    }

    override fun getIoHandlerAdapter(): IoHandlerAdapter {
        return ClientIoHandlerAdapter()
    }

    override fun getKeepAliveFilter(): KeepAliveMessageFactory {
        return KeepAliveMsgFactory()
    }

    override fun getIoServiceListener(listener: ConnectListener): IoServiceListener {
        return ClientIoServiceListener(listener)
    }

    override fun sessionClosed() {
        getSession()?.close()
    }

    fun sendMessage(content:String){
        getSession()?.write(content)
    }
}