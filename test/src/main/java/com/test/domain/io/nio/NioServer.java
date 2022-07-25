package com.test.domain.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NioServer
 *
 * @author torrisli
 * @date 2022/7/25
 * @Description: NioServer
 */
public class NioServer {

    public static void main(String[] args) throws IOException {

        // 1)、获取通道。当客户端连接服务端时，服务端会通过 ServerSocketChannel 得到 SocketChannel：
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        // 2)、切换非阻塞模式
        ssChannel.configureBlocking(false);
        // 3)、绑定连接
        ssChannel.bind(new InetSocketAddress(8888));
        // 4)、获取选择器
        Selector selector = Selector.open();
        // 5)、将通道注册到选择器上, 并且指定“监听接收事件”
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 6)、轮询式的获取选择器上已经“准备就绪”的事件
        while (selector.select() > 0) {
            System.out.println("开启事件处理");
            // 7.获取选择器中所有注册的通道中已准备好的事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            // 8.开始遍历事件
            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();
                System.out.println("--->" + selectionKey);
                //9.判断这个事件具体是啥
                if (selectionKey.isAcceptable()) {
                    // 10.获取当前接入事件的客户端通道
                    SocketChannel socketChannel = ssChannel.accept();
                    // 11.切换成非阻塞模式
                    socketChannel.configureBlocking(false);
                    // 12.将本客户端注册到选择器
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    // 13.获取当前选择器上的读
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    // 14.读取
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len;
                    while ((len = socketChannel.read(buffer)) > 0) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, len));
                        // 清除之前的数据（覆盖写入）
                        buffer.clear();
                    }
                }
                // 15.处理完毕后，移除当前事件
                it.remove();
            }
        }

    }
}
