package com.test.domain.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * SelectorTest
 *
 * @author torrisli
 * @date 2022/7/25
 * @Description: SelectorTest
 */
public class SelectorTest {

    public static void main(String[] args) throws IOException {

        // 1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        // 2. 切换非阻塞模式
        ssChannel.configureBlocking(false);
        // 3. 绑定连接
        ssChannel.bind(new InetSocketAddress(9898));
        // 4. 获取选择器
        Selector selector = Selector.open();
        // 5. 将通道注册到选择器上, 并且指定“监听接收事件”
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

    }
}
