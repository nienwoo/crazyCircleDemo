package com.crazymakercircle.NettyTest;

import com.crazymakercircle.netty.handler.LifeCircleDemoHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class TestInHandlerDemo {


    @Test
    public void testInHandlerLifeCircle() {
        final LifeCircleDemoHandler handler = new LifeCircleDemoHandler();
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(handler);
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(i);

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        channel.writeInbound(buf);
        channel.flush();
        channel.writeInbound(buf);
        channel.flush();

//        Object o= channel.readInbound();
//        System.out.println("o = " + o);
//        channel.pipeline().remove(handler);

        channel.finish();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
