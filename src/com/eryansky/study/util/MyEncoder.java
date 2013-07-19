package com.eryansky.study.util;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * 
 * @ClassName: MyEncoder 
 * @Description: mina编码器 
 * @author 尔演&Eryan eryanwcp@163.com
 * @date 2011-11-23 下午01:57:51 
 *
 */
public class MyEncoder extends ProtocolEncoderAdapter {
    private final AttributeKey ENCODER = new AttributeKey(getClass(), "encoder");
    private final Charset charset;

    public MyEncoder(Charset charset) {
        this.charset = charset;
    }

    public MyEncoder() {
        this(Charset.defaultCharset());
    }

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        CharsetEncoder encoder = (CharsetEncoder) session.getAttribute(ENCODER);
        if (encoder == null) {
            encoder = charset.newEncoder();
            session.setAttribute(ENCODER, encoder);
        }

        String value = message.toString();
        IoBuffer buf = IoBuffer.allocate(value.length())
                .setAutoExpand(true);
        buf.putString(value, encoder);
        if (buf.position() > 9999) {
            throw new IllegalArgumentException("Line length: " + buf.position());
        }
        //buf.putString(delimiter.getValue(), encoder);
        buf.flip();
        out.write(buf);
    }

    public void dispose() throws Exception {
    }
}
