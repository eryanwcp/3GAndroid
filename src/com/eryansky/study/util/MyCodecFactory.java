package com.eryansky.study.util;
import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 
 * @ClassName: MyCodecFactory 
 * @Description: 自定义ProtocolCodecFactory 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-11-23 下午01:59:08 
 *
 */
public class MyCodecFactory implements ProtocolCodecFactory {
    private final MyEncoder encoder;
    private final MyDecoder decoder;


    public MyCodecFactory(Charset charset) {
        encoder = new MyEncoder(charset);
        decoder = new MyDecoder(charset);
    }

    public MyCodecFactory() {
        this(Charset.forName("UTF-8"));//设定mina通信报文编码方式
        //this(Charset.forName("GBK"));
    }

    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
}

