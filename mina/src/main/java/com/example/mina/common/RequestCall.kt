package com.example.mina.common

interface RequestCall {
    fun success(content: String?)
    fun fail(msg: String?)
}