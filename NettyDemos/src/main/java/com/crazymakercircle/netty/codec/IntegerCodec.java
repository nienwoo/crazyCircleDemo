package com.crazymakercircle.netty.codec;

import com.crazymakercircle.netty.decoder.IntegerFromByteDecoder;
import com.crazymakercircle.netty.encoder.IntegerToByteEncoder;
import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class IntegerCodec extends CombinedChannelDuplexHandler<
        IntegerFromByteDecoder,
        IntegerToByteEncoder>
{
    public IntegerCodec() {
        super(new IntegerFromByteDecoder(), new IntegerToByteEncoder());
    }
}
