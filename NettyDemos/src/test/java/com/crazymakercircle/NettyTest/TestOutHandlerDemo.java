package com.crazymakercircle.NettyTest;

import com.crazymakercircle.netty.handler.OutHandlerDemoHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class TestOutHandlerDemo {


    @Test
    public void testlifeCircle() {
        final OutHandlerDemoHandler handler = new OutHandlerDemoHandler();
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(handler);
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(i);

//        channel.pipeline().addLast(handler);

        //测试出站写入

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);

        ChannelFuture f = channel.pipeline().writeAndFlush(buf);

        f.addListener((future) -> {

            if (future.isSuccess()) {
                System.out.println("write is finished");

            }

            channel.finish();
        });

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
