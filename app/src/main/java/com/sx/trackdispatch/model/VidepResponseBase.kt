package com.sx.trackdispatch.model

class VidepResponseBase<T> {
    var code = ""
    var msg: String = ""
    var data: T? = null
}