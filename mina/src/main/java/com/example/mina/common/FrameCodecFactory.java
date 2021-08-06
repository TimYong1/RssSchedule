package com.example.mina.common;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created by xiao on 2017/7/28.
 */

public class FrameCodecFactory implements ProtocolCodecFactory {
    private FrameEncoder frameEncoder = new FrameEncoder();
    private FrameDecoder frameDecoder = new FrameDecoder();

    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return frameEncoder;
//        return new FrameEncoder();
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return frameDecoder;
//        return new FrameDecoder();
    }
}
