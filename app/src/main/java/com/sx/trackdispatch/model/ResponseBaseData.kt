package com.sx.trackdispatch.model

class ResponseBaseData<T> {
    var errorCode = 0
    var errorMsg: String = ""
    var data: T? = null
}