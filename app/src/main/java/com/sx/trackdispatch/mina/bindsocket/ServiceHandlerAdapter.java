package com.sx.trackdispatch.mina.bindsocket;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONObject;
import com.armslocker.util.MessageConst;
import com.example.mina.common.SessioManage;
import com.sx.armslockermvvm.mina.messagehandle.HeartbeatHandler;
import com.sx.armslockermvvm.mina.messagehandle.MessageHandlerContext;
import com.sx.armslockermvvm.mina.messagehandle.service.MessageHandlerFactory;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 主机 --> 从机  主机接收从机消息
 */

public class ServiceHandlerAdapter extends IoHandlerAdapter {
    private final String TAG = "ServiceNet";
    private HeartbeatHandler heartbeatHandler;
    private MessageHandlerContext messageHandlerContext;
    // 从端口接受消息，会响应此方法来对消息进行处理
    public ServiceHandlerAdapter(){

    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        formatData(message, session);
    }

    public void formatData(Object c, IoSession session) {
        try {
            Log.d(TAG, "服务器接受消息" + c.toString());

            if("0".equals(c.toString())){
                if(heartbeatHandler==null){
                    heartbeatHandler = new HeartbeatHandler();
                }
                heartbeatHandler.handleReceipt(session,JSONObject.parseObject("{}"));
                return;
            }
            JSONObject data = JSONObject.parseObject(c.toString());
            int type = data.getIntValue(MessageConst.MESSAGETYPE);
            if(messageHandlerContext==null){
                messageHandlerContext = new MessageHandlerContext<Integer>(new MessageHandlerFactory());
            }
            SessioManage.Companion.getInstance().getServiceSession().put(data.getString(MessageConst.PADKEY),session);

            messageHandlerContext.handlerMessage(type,session,data);
        } catch (Exception e) {
            Log.d(TAG,"解析baseData错误");
            e.printStackTrace();
        }
    }

    // 向客服端发送消息后会调用此方法
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
//        session.close(true);//加上这句话实现短连接的效果，向客户端成功发送数据后断开连接
        Log.d(TAG, "服务器发送消息成功"+message.toString());
    }

    // 关闭与客户端的连接时会调用此方法
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        Log.d(TAG, "服务器与客户端断开连接...");
    }

    // 服务器与客户端创建连接
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        Log.d(TAG, "服务器与客户端创建连接...");
    }

    // 服务器与客户端连接打开
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        Log.d(TAG, "服务器与客户端连接打开...");
        super.sessionOpened(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        Log.d(TAG, "服务器进入空闲状态...");
//        SessionManager.getInstance().writeToServer("0");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        Log.d(TAG, "服务器发送异常..."+cause.getMessage());
    }

}
