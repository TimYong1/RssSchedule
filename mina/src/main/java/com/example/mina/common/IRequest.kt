package com.example.mina.common

import com.alibaba.fastjson.JSONObject
import com.example.mina.common.RequestCall
import org.apache.mina.core.session.IoSession

interface IRequest {
    fun request(content: JSONObject,call: RequestCall)
}