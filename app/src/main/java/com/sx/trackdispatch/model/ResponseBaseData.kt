package com.sx.trackdispatch.model

class ResponseBaseData<T> {
    var code = 0
    var msg: String = ""
    var data: T? = null
    var additionalInformation = ""
    var count = ""
}