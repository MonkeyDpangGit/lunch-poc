package com.test.domain.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.StandardCharsets;

/**
 * NettyClientHandler
 *
 * @author torrisli
 * @date 2022/7/27
 * @Description: NettyClientHandler
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接服务端完成时，会触发这个方法
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf buf = Unpooled.copiedBuffer("helloServer", StandardCharsets.UTF_8);
        ctx.writeAndFlush(buf);

    }

    /**
     * 当通道有读取事件时，会触发。服务端发送数据给客户端时
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf buf = (ByteBuf)msg;
        System.out.println("收到服务端消息：" + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
