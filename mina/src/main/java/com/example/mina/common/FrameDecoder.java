package com.example.mina.common;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;
import android.os.Handler;

import androidx.annotation.NonNull;

/**
 * Created by xiao on 2017/7/28.
 */

public class FrameDecoder extends CumulativeProtocolDecoder {
    private Charset charset = Charset.forName("UTF-8");
    // 可变的IoBuffer数据缓冲区
    private IoBuffer buff;

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        if(buff==null){
            buff = IoBuffer.allocate(1024*1024).setAutoExpand(true);
        }
        // 如果有消息
        while (ioBuffer.hasRemaining()) {
            byte b = ioBuffer.get();

            if (b == '\n') {
                buff.flip();
                byte[] bytes = new byte[buff.limit()];
                buff.get(bytes);
                String message = new String(bytes, charset);
                Log.d("clientNet","接收到数据:"+message);
                // 如果结束了，就写入转码后的数据
                message = message.replace("\r","");
                protocolDecoderOutput.write(message);
                buff = IoBuffer.allocate(1024*1024).setAutoExpand(true);
            } else {
                buff.put(b);
            }
        }
        return false;
    }
}
