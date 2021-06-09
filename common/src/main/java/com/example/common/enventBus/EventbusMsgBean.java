package com.example.common.enventBus;

public class EventbusMsgBean<T> {
    public int messageCode;
    public T t;

    public EventbusMsgBean(int messageCode, T t) {
        this.messageCode = messageCode;
        this.t = t;
    }

    public EventbusMsgBean(int messageCode) {
        this.messageCode = messageCode;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
