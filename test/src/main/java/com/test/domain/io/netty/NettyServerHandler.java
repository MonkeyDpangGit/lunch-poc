package com.test.domain.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.nio.charset.StandardCharsets;

/**
 * NettyServerHandler
 *
 * @author torrisli
 * @date 2022/7/27
 * @Description: NettyServerHandler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * channel连接事件
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        ctx.writeAndFlush(channel.remoteAddress() + "，已经连接");
    }

    /**
     * 读取客户端发送的数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("服务读取线程：" + Thread.currentThread().getName());
//        ByteBuf buf = (ByteBuf)msg;
        System.out.println("接收客户端消息：" + msg.toString());
    }


    /**
     * 读取完毕的处理方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ByteBuf buf = Unpooled.copiedBuffer("helloClient", StandardCharsets.UTF_8);
        ctx.writeAndFlush(buf);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
