package com.test.domain.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * SingleThreadServer
 *
 * @author torrisli
 * @date 2022/7/20
 * @Description: SingleThreadServer
 */
public class SingleThreadServer {

    public static void main(String[] args) throws IOException {

        System.out.println("服务器启动.....");

        Socket socket = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            // 1.定义一个serverSocket，并定义端口是 9999
            ServerSocket ss = new ServerSocket(9999);
            // 2.监听k客户端的socket请求
            socket = ss.accept();
            // 3.从客户端传来的socket里面获取到流
            is = socket.getInputStream();
            // 4.将字节输入流包装成一个缓冲字符输入流
            br = new BufferedReader(new InputStreamReader(is));
            String msg;
            while (true) {
                if ((msg = br.readLine()) != null) {
                    System.out.println("服务端线程（" + Thread.currentThread().getId() + "）接受到信息：" + msg);
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                System.out.println("服务端线程（" + Thread.currentThread().getId() + "）关闭和client的连接");
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
