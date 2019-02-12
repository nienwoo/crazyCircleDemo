package com.crazymakercircle.NettyTest;

import com.crazymakercircle.netty.encoder.IntegerToByteEncoder;
import com.crazymakercircle.netty.encoder.StringToIntegerEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class TestEncoder {

    /**
     * 测试整数编码器
     */
    @Test
    public void testIntegerToByteDecoder() {
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new IntegerToByteEncoder());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(i);

        for (int j = 0; j < 100; j++) {
            channel.write(j);
        }
        channel.flush();
        ByteBuf buf = (ByteBuf) channel.readOutbound();
        while (null != buf) {
            System.out.println("o = " + buf.readInt());
            buf = (ByteBuf) channel.readOutbound();
        }
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

 /**
     * 测试字符串到整数编码器
     */
    @Test
    public void testStringToIntergerDecoder() {
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new IntegerToByteEncoder());
                ch.pipeline().addLast(new StringToIntegerEncoder());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(i);

        for (int j = 0; j < 100; j++) {
            String s="i am "+j;
            channel.write(s);
        }
        channel.flush();
        ByteBuf buf = (ByteBuf) channel.readOutbound();
        while (null != buf) {
            System.out.println("o = " + String.valueOf( buf.readInt()-48));
            buf = (ByteBuf) channel.readOutbound();
        }
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
