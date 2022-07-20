package com.test.domain.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * SimpleClient
 *
 * @author torrisli
 * @date 2022/7/20
 * @Description: SimpleClient
 */
public class SimpleClient {

    public static void main(String[] args) throws IOException {

        // 1.创建socket将对象请求服务端的链接
        Socket socket = new Socket("127.0.0.1", 9999);
        // 2.从socket对象中获取一个字符输出流
        OutputStream os = socket.getOutputStream();
        PrintStream ps = new PrintStream(os);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请说：");
            String msg = scanner.nextLine();
            ps.println(msg);
            ps.flush();
        }
    }
}
