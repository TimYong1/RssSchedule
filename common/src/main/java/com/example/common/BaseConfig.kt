package com.armslocker.util

object BaseConfig {
    const val rePeatConnectTime = 10L //断线重连间隔时间
    const val rePeatServiceTime = 30L //服务重启间隔时间
    const val minaTimeOut = 5000L//长链接消息超时时间
    const val hostPort = 8899
}