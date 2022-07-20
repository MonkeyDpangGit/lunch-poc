package com.test.domain.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * InteractServer
 *
 * @author torrisli
 * @date 2022/7/20
 * @Description: InteractServer
 */
public class InteractServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket server = new ServerSocket(55535);
        System.out.println("服务器启动完毕");

        // 第一次
        Socket socket = server.accept();

//        OutputStream out = socket.getOutputStream();
//        PrintStream ps = new PrintStream(out);
//        ps.println(Thread.currentThread().getName() + "欢迎你连接服务器");
//        socket.getOutputStream().flush();

        InputStream in = socket.getInputStream();
        ObjectInputStream obj = new ObjectInputStream(in);
        System.out.println(obj.readObject());
        System.out.println("第一次");

        // 第二次
//        InputStream in1 = socket.getInputStream();
//        socket.getOutputStream().flush();
//        ObjectInputStream obj1 = new ObjectInputStream(in1);
//        System.out.println(obj1.readObject());

        OutputStream out1 = socket.getOutputStream();
        PrintStream ps1 = new PrintStream(out1);
        ps1.println(Thread.currentThread().getName() + "欢迎你连接服务器");
        System.out.println("第二次");

        socket.close();
    }
}
