package com.armslocker.util

object MessageConst {
    val CONNETCLOSED = 400 //连接成功
    val CONNETSUCCESS = 200 //连接成功
    val RECEIVEMESSAGE = 100 //接收消息

    const val RECEIVESERVER = 10001//主动发送消息
    const val RECEIVESERVERFILE = 10002//文件传输
    const val RECEIVESERVERCLIENT = 10003//接收消息成功回执类型
    const val DOOROPENINGAUTHORIZATION = 10005//开门授权通过或不通过
    const val DOOROPENGETID = 10006//获取到id,对方登录成功
    const val DOOROPENINGAUTHORIZATIONOK = "dooropeningauthorizationok"//开门授权通过
    const val DOOROPENINGAUTHORIZATIONNO = "dooropeningauthorizationno"//开门授权不通过
    const val MESSAGETYPE = "messageType"//互相传递消息类型
    const val MSGNUNBER = "msgNumber"//发送消息编号，用于确认消息发送成功失败
    const val MSGDATA = "msgData"//互相传递消息内容
    const val PADKEY = "padKey"//识别是哪个平板发送的
    const val SENDPADKEY = "sendPadKey"//识别发送给哪个平板
    const val SYNCHRONIZED = 1//已经同步
    const val RETURNTYPE = "returnType" //返回的状态成功还是失败


    //针对主机到服务端
    const val MESSAGEID = "messageId"//消息id

    const val LOGTAG = "logTag"//日志
    const val ARMSLOCKERTAG = "armslockerTag"//枪柜
    const val GUNTAG = "gunTag"//枪支
    const val PERSONTAG = "personTag"//人员
    const val ALERTTAG = "alertTag"//警报
    const val APPUPDATE = "appupdate"//app更新
    const val OPENARMSLOCKERTAG = "openArmsLockerTag"//主机请求开柜
    const val FINGERPRINTTAG = "FingerprintTag"//同步指纹数据
    const val DEFENSETAG = "defenseTag"//布防
    const val CONNECTAG = "connectTag"//连接
    const val TEMPERATUREORHUMIDITYCHANGETAG = "temperatureorhumiditychangetag"//温湿度变化

    const val ALERTSHOW = "alertshow"//报警显示
    const val ALERTDISMISS = "alertdismiss"//报警取消


    const val DEVICETYPE = "deviceType"//类型
    const val OPENNUNBER = "opennunber"//人脸识别页面打开次数

    const val ADDADMIN = "addAdmin"//添加超级管理员
    const val SYNADD = "synadd"//同步消息---添加
    const val SYNLIST = "synlist"//同步消息---集合
    const val SYNADDLIST = "synaddlist"//同步消息---集合
    const val SYNGET = "synget"//消息获取消息
    const val SYNUPDATE = "synupdate"//同步消息---更新
    const val SYNDELETE = "syndelete"//同步消息---删除
    const val SYNQUERY = "synquery"//同步消息---查询
    const val SYNCONNECT = "synconnect"//连接成功
    const val SYNARMSLOCKERLIST = "synArmslockerList"//连接成功
    const val ARMSLOCKEROFFCHANGE = "ArmsLockerOffChange"//枪柜在线变化
    const val CLEARALARM = "clearalarm"//解除警报
    const val MAINRECEIVERCODR = "mainReceiverCODR"//广播code
    const val CLEARALARMCODE = 8008//解除警报
    const val APPLYOPENARMSLOCKER = 8009//解除警报
    const val ADDPERSONCODE = 8010//添加人员
//    const val SYNALERT = "synalert"//

    const val GUNUPDATEMEESAGE = 3001//枪支更新通知
    const val ARMSLOCKERUPDATEMEESAGE = 3002//枪柜更新通知
    const val LOGUPDATEMEESAGE = 3003//log更新通知
    const val SERIALPORTGUNCHANGE = 3004//串口数据枪支在位变化
    const val SERIALPORTGUNCHANGEBYARMSLOCKER = 3005//串口数据枪支在位变化

    const val SYNPERSONSCODE = 4000//人员同步完成
    const val SYNARMSLOCKERSCODE = 4001//枪柜同步完成
    const val SYNPERSONSOK = "synpersonsok"//人员同步成功
    const val SYNPERSONSTIMEOUT = "synpersonstimeout"//人员同步超时

    const val SENDCODE = 4011//发送指令
    const val TIMECHANGE = 4010//时间变化

    const val ONLINECHANGE = 10//在线变化
    const val ONLINE = "onLine"//在线
    const val OFFLINE = "offLine"//离线
    const val ARMSLOCKEROFFLINE = 4030//枪柜离线变化
    const val RTCLOGINCHANGE = 4031//rtc登录成功


    const val SETTINGCHANGE = 11//设置变化
    const val ADDPERSONCHANGE = 12//人员数据变化
    const val ADDGUNCHANGE = 14//添加枪械成功
    const val UPDATEWITHDRAWGARRISON = 20002//更新撤防状态
    const val SHOWALERTDIALOG = 20000//显示警报信息
    const val SEARCHDWEVICE = 20001//显示警报信息
    const val APPUPDATECODE = 20005//更新App code

    const val TEMPERATUREORHUMIDITYCHANGE = 20//温度湿度变化

    const val INITARMSLOCKER = "initarmslocker"//
}