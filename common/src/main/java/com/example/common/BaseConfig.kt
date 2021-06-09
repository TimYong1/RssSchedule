package com.armslocker.util

object BaseConfig {
    const val timeLimit = 1000*60*60*24*365 //激活期限  默认一年
    const val rePeatConnectTime = 10L //断线重连间隔时间
    const val rePeatServiceTime = 30L //服务重启间隔时间
    const val synTime = 60000//自动同步数据间隔时间
    const val minaTimeOut = 5000L//长链接消息超时时间
    const val serialTimeOut = 500//串口通信消息超时时间
    const val baudRate = 115200//波特率
    const val sendInterval = 200//串口发送间隔
    const val serialportFormatTime = 2500L//解析串口数据时间间隔，时间太短会卡顿
    const val device = "/dev/ttyS3"//串口地址
    const val serialportMessageNumber = 3//串口消息发送次数，超过次数将取消发送
    const val restartMaxNumber = 20//重启值--子进程开启次数达到杀掉子进程
    const val faceRegisterCheckTime = 3000L//人脸信息注册，验重时间 3秒内未检测到人脸可以注册
    const val hostPort = 8899
    const val synPersonsTimeOut = 4000L//人员同步超时时间
    const val videoPlayTimeOut = 60000//视频通话超时时间
    const val armsLockerTimeOut = 80000L // 枪柜断线时间
}