package com.crazymakercircle.NettyTest;

import com.crazymakercircle.netty.decoder.IntegerFromByteDecoder;
import com.crazymakercircle.netty.decoder.IntegerProcessHandler;
import com.crazymakercircle.netty.decoder.StringFromIntegerDecoder;
import com.crazymakercircle.netty.decoder.StringProcessHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class TestDecoder {
    /**
     * 整数解码器的使用实例
     */
    @Test
    public void testByteToIntegerDecoder() {
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new IntegerFromByteDecoder());
                ch.pipeline().addLast(new IntegerProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);

        for (int j = 0; j < 100; j++) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(j);
            channel.writeInbound(buf);
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 整数to 字符串解码器的使用实例
     */
    @Test
    public void testIntegerToStringDecoder() {
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new IntegerFromByteDecoder());
                ch.pipeline().addLast(new StringFromIntegerDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);

        for (int j = 0; j < 100; j++) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(j);
            channel.writeInbound(buf);
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * LineBasedFrameDecoder 使用实例
     */
    @Test
    public void testLineBasedFrameDecoder() {
        try {
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                protected void initChannel(EmbeddedChannel ch) {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);

            for (int j = 0; j < 100; j++) {
                ByteBuf buf = Unpooled.buffer();
                String s = "I am " + j;
                buf.writeBytes(s.getBytes("UTF-8"));
                buf.writeBytes("\r\n".getBytes("UTF-8"));

                channel.writeInbound(buf);
            }


            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * LineBasedFrameDecoder 使用实例
     */
    @Test
    public void testLengthFieldBasedFrameDecoder() {
        try {

           final LengthFieldBasedFrameDecoder spliter=
                    new LengthFieldBasedFrameDecoder(1024,0,4,0,4);
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                protected void initChannel(EmbeddedChannel ch) {
                    ch.pipeline().addLast(spliter);
                    ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);

            for (int j = 0; j < 100; j++) {
                ByteBuf buf = Unpooled.buffer();
                String s = j+ " is me ,呵呵" ;
                byte[] bytes = s.getBytes("UTF-8");
                buf.writeInt(bytes.length);
                buf.writeBytes(bytes);

                channel.writeInbound(buf);
            }


            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }   /**
     * LineBasedFrameDecoder 使用实例
     */
    @Test
    public void testLengthFieldBasedFrameDecoder2() {
        try {

           final LengthFieldBasedFrameDecoder spliter=
                    new LengthFieldBasedFrameDecoder(1024,2,4,-4,6);
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                protected void initChannel(EmbeddedChannel ch) {
                    ch.pipeline().addLast(spliter);
                    ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);

            for (int j = 0; j < 100; j++) {
                ByteBuf buf = Unpooled.buffer();
                String s = j+ " is me ,呵呵" ;
                byte[] bytes = s.getBytes("UTF-8");
                buf.writeChar(100);
                buf.writeInt(bytes.length+4);
                buf.writeBytes(bytes);

                channel.writeInbound(buf);
            }


            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
