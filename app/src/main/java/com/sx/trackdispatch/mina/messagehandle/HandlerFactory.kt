package com.sx.armslockermvvm.mina.messagehandle

interface HandlerFactory<E> {
    fun initMap()
    fun get(key:E):IMessageHandlerStrategy
}