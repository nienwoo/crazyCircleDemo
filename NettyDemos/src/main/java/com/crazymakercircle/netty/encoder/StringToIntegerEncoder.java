package com.crazymakercircle.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class StringToIntegerEncoder extends MessageToMessageEncoder<String> {


    @Override
    protected void encode(ChannelHandlerContext c, String s, List<Object> list) throws Exception {
        char[] array = s.toCharArray();
        for (char a : array) {
            //48 是0的编码，57 是9 的编码
            if (a >= 48 && a <= 57) {
                list.add(new Integer(a));
            }
        }
    }
}
