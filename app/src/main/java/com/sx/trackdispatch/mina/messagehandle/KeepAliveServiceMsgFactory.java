package com.sx.trackdispatch.mina.messagehandle;

import com.alibaba.fastjson.JSONObject;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import java.util.UUID;

public class KeepAliveServiceMsgFactory implements KeepAliveMessageFactory {
    @Override
    public boolean isRequest(IoSession ioSession, Object o) {
        //如果是客户端主动向服务器发起的心跳包, return true, 该框架会发送 getRequest() 方法返回的心跳包内容.
        if("0".equals(o.toString())){
            return true;
        }
        return false;
    }

    @Override
    public boolean isResponse(IoSession ioSession, Object o) {
        //如果是服务器发送过来的心跳包, return true后会在 getResponse() 方法中处理心跳包.
        if("0".equals(o.toString())){
            return true;
        }
        return false;
    }

    @Override
    public Object getRequest(IoSession ioSession) {
        //自定义向服务器发送的心跳包内容.
        JSONObject obj = new JSONObject();
        obj.put("messageId", UUID.randomUUID().toString());
        obj.put("deviceType", "枪柜");
        obj.put("deviceNo", android.os.Build.SERIAL);
        obj.put("dataType", "connect");
        return obj.toJSONString();
    }

//    private void sendHeartMessage(IoSession ioSession){

//        ioSession.write(obj.toJSONString());
//    }

    @Override
    public Object getResponse(IoSession ioSession, Object o) {
        //自定义解析服务器发送过来的心跳包.
        try{
            if("0".equals(o.toString())){
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return o;
    }
}
