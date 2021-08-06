package com.sx.armslockermvvm.mina.messagehandle.service

import android.os.Handler
import android.util.Log
import com.alibaba.fastjson.JSONObject
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession
import java.io.File

/**
 * 主机-->服务器 接受消息类
 */
class ServiceIoHandlerAdapter: IoHandlerAdapter() {
    private val TAG = "ServiceIoHandlerAdapter"
    private val SAVE_FEATURE_DIR = "register" + File.separator + "features"

    @Throws(Exception::class)
    override fun exceptionCaught(session: IoSession?, cause: Throwable) {
        Log.d(TAG, "exceptionCaught: 异常信息$cause")
    }

    override fun messageReceived(session: IoSession, message: Any) {
        Log.d(TAG, "接收到服务器端消息：$message")
        formatData(message.toString(), session)
    }

    private fun formatData(content: Any, session: IoSession) {
        try {

        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }
    }

    private fun returnSuccessData(
        session: IoSession,
        msgKey: String,
        code: Int,
        message: String,
        dataType: String,
        deviceNo: String
    ) {
        val jsonObject = JSONObject()
        jsonObject["code"] = code
        jsonObject["messageId"] = msgKey
        jsonObject["message"] = message
        jsonObject["dataType"] = dataType
        jsonObject["deviceNo"] = deviceNo
        jsonObject["data"] = JSONObject()
        session.write(jsonObject.toJSONString())
        Log.d("服务器发送消息", jsonObject.toJSONString())
    }

    @Throws(Exception::class)
    override fun inputClosed(ioSession: IoSession?) {
        super.inputClosed(ioSession)
    }

    @Throws(Exception::class)
    override fun messageSent(session: IoSession?, message: Any?) {
        super.messageSent(session, message)
        Log.d(TAG, " HeartBeatHandler: 客户端调用messageSent")
        //        session.close(true);//加上这句话实现短连接的效果，向客户端成功发送数据后断开连接
    }

    @Throws(Exception::class)
    override fun sessionClosed(session: IoSession?) {
        Log.d(TAG, "HeartBeatHandler: 客户端调用sessionClosed")
        super.sessionClosed(session)
    }

    @Throws(Exception::class)
    override fun sessionCreated(session: IoSession?) {
        Log.d(TAG, "HeartBeatHandler: 客户端调用sessionCreated")
        super.sessionCreated(session)
    }

    @Throws(Exception::class)
    override fun sessionIdle(session: IoSession, status: IdleStatus?) {
        super.sessionIdle(session, status)
        session.close(true)
        Log.d(TAG, "HeartBeatHandler: 客户端调用sessionIdle")
    }

    @Throws(Exception::class)
    override fun sessionOpened(session: IoSession?) {
        super.sessionOpened(session)
        Log.d(TAG, "HeartBeatHandler: 客户端调用sessionOpened")
    }

//    fun spliteDir(dir: String, file: String?) {
//        if (TextUtils.isEmpty(dir) || TextUtils.isEmpty(file)) {
//            return
//        }
//        val start = dir.lastIndexOf("/") + 1
//        val name = dir.substring(start, dir.length)
//        val ROOT_PATH: String = MyApplication.application.getFilesDir().getAbsolutePath()
//        val featureDir =
//            ROOT_PATH + File.separator + SAVE_FEATURE_DIR + File.separator
//        //        sendFile(featureDir.path + File.separator + "registered 1")
////        String dirs = featureDir;
////        dirs = dirs+"1/";
//        FileUtil.Companion.getFile(file, featureDir, name, object : ByteArrayToFileListener() {
//            fun success(path: String) {
//                Log.d("测试", "文件生成成功$path")
//            }
//
//            fun fail(fail: String?) {
//                Log.d("测试", fail)
//            }
//        })
//    }
}