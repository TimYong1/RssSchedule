package com.example.common.bean;

public class EventBusMeesage<T> {
    public int messageCode;
    public T t;

    public EventBusMeesage(int messageCode, T t) {
        this.messageCode = messageCode;
        this.t = t;
    }

    public EventBusMeesage(int messageCode) {
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
