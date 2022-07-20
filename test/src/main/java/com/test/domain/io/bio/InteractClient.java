package com.test.domain.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * InteractClient
 *
 * @author torrisli
 * @date 2022/7/20
 * @Description: InteractClient
 */
public class InteractClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 55535);
        // 第一次
//        InputStream in = socket.getInputStream();
//        InputStreamReader inputStreamReader = new InputStreamReader(in);
//        BufferedReader reader = new BufferedReader(inputStreamReader);
//        System.out.println(reader.readLine());

        OutputStream out = socket.getOutputStream();
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("user", 1);
        ObjectOutputStream obj = new ObjectOutputStream(out);
        obj.writeObject(hashMap);

//        obj.close();
        System.out.println(socket.isClosed());
        //socket.getOutputStream().flush();

        // 第二次
        InputStream in1 = socket.getInputStream();
        InputStreamReader inputStreamReader1 = new InputStreamReader(in1);
        BufferedReader reader1 = new BufferedReader(inputStreamReader1);
        System.out.println(reader1.readLine());

//        OutputStream out1 = socket.getOutputStream();
//        ObjectOutputStream obj1 = new ObjectOutputStream(out1);
//        obj1.writeObject(hashMap);
        //obj1.close();
        //socket.getOutputStream().flush();
    }
}
