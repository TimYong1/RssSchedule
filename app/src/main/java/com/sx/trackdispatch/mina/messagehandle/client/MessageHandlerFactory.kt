package com.sx.armslockermvvm.mina.messagehandle.client

import com.armslocker.util.MessageConst
import com.sx.armslockermvvm.mina.messagehandle.HandlerFactory
import com.sx.armslockermvvm.mina.messagehandle.IMessageHandlerStrategy


class MessageHandlerFactory: HandlerFactory<Int> {
    private lateinit var map: MutableMap<Int, IMessageHandlerStrategy>

    init {
        initMap()
    }

    override fun initMap() {
        map = mutableMapOf()
        map.put(MessageConst.RECEIVESERVERCLIENT, SendMessageSuccessHandle())
        map.put(MessageConst.RECEIVESERVER, ReceiveMessageHandle())
    }

    override fun get(key: Int): IMessageHandlerStrategy {
        return map.get(key)!!
    }
}