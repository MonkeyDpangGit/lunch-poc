package com.test.domain.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * NioClient
 *
 * @author torrisli
 * @date 2022/7/25
 * @Description: NioClient
 */
public class NioClient {

    public static void main(String[] args) throws IOException {

        // 1)、获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        // 2)、切换非阻塞模式
        sChannel.configureBlocking(false);
        // 3)、分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 4)、发送数据给绑定的服务端
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String str = scan.nextLine();
            buffer.put((new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis())
                    + "\n" + str).getBytes());
            buffer.flip();
            sChannel.write(buffer);
            buffer.clear();
        }
        // 关闭通道
        sChannel.close();
    }
}
