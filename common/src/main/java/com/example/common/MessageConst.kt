package com.armslocker.util

object MessageConst {

    const val RECEIVESERVER = 10001//主动发送消息
    const val RECEIVESERVERFILE = 10002//文件传输
    const val RECEIVESERVERCLIENT = 10003//接收消息成功回执类型


    const val MESSAGETYPE = "messageType"//互相传递消息类型
    const val MSGDATA = "msgData"//互相传递消息内容
    const val PADKEY = "padKey"//识别是哪个平板发送的
    const val SENDPADKEY = "sendPadKey"//识别发送给哪个平板
    const val RETURNTYPE = "returnType" //返回的状态成功还是失败
    //针对主机到服务端
    const val MESSAGEID = "messageId"//发送消息编号，用于确认消息发送成功失败

}