package com.manning.nettyinaction.chapter1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Listing 1.3 and 1.4 of <i>Netty in Action</i>
 * 每个netty的出站I/O操作都将返回一个ChannelFuture，Netty完全是异步和事件驱动的
 * @author <a href="mailto:norman.maurer@googlemail.com">Norman Maurer</a>
 */
public class ConnectExample {

    public static void connect(Channel channel) {
        // Does not block
        ChannelFuture future = channel.connect(
                new InetSocketAddress("192.168.0.1", 25));
        future.addListener(new ChannelFutureListener() {

        public void operationComplete(ChannelFuture future) {
            if (future.isSuccess()) {
                ByteBuf buffer = Unpooled.copiedBuffer(
                        "Hello", Charset.defaultCharset());
                ChannelFuture wf = future.channel().writeAndFlush(buffer);
                // ...
            } else {
                Throwable cause = future.cause();
                cause.printStackTrace();
            }
        }
        });

    }
}
