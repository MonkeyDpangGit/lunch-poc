package com.test.domain.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * ChannelTest
 *
 * @author torrisli
 * @date 2022/7/25
 * @Description: https://blog.csdn.net/K_520_W/article/details/123454627
 */
public class ChannelTest {

    public static void write() {

        try {
            // 1、字节输出流通向目标文件
            FileOutputStream fos = new FileOutputStream("/Users/lijunhao/Desktop/temp/data01.txt");
            // 2、得到字节输出流对应的通道Channel
            FileChannel channel = fos.getChannel();
            // 3、分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            for (int i = 0; i < 10; i++) {
                buffer.clear(); // 清空缓冲区
                buffer.put(("hello,使用Buffer和channel实现写数据到文件中" + i + "\r\n").getBytes());
                // 4、把缓冲区切换成写出模式
                buffer.flip();
                channel.write(buffer); // 将缓冲区的数据写入到文件通道
            }
            channel.close();
            System.out.println("写数据到文件中！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void read() throws Exception {

        // 1、定义一个文件字节输入流与源文件接通
        FileInputStream is = new FileInputStream("/Users/lijunhao/Desktop/temp/data01.txt");
        // 2、需要得到文件字节输入流的文件通道
        FileChannel channel = is.getChannel();
        // 3、定义一个缓冲区
        int bufferSize = 1024 * 1024; // 每一块的大小
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

        ByteBuffer bb = ByteBuffer.allocate(1024);

        // 4、读取数据到缓冲区
        int bytesRead = channel.read(buffer);
        while (bytesRead != -1) {
            buffer.flip(); // 切换模式，写->读
            while (buffer.hasRemaining()) { // 返回 position 和 limit 之间的元素个数
                byte b = buffer.get();
                if (b == 10 || b == 13) { // 换行或回车
                    bb.flip();
                    // 这里就是一个行
                    final String line = Charset.forName("utf-8").decode(bb).toString();
                    System.out.println(line); // 解码已经读到的一行所对应的字节
                    bb.clear();
                } else {
//                    if (bb.hasRemaining()) {
//                        bb.put(b);
//                    } else { // 空间不够扩容
//                        bb = reAllocate(bb);
//                        bb.put(b);
//                    }
                    bb.put(b);
                }
            }
            buffer.clear(); // 清空,position位置为0，limit=capacity
            // 继续往buffer中写
            bytesRead = channel.read(buffer);
        }
        channel.close();
    }

    public static void copy() throws Exception {

        // 源文件
        File srcFile = new File("/Users/lijunhao/Desktop/temp/Aurora-4k.jpg");
        File destFile = new File("/Users/lijunhao/Desktop/temp/Aurora-4k-new.jpg");
        // 得到一个字节字节输入流
        FileInputStream fis = new FileInputStream(srcFile);
        // 得到一个字节输出流
        FileOutputStream fos = new FileOutputStream(destFile);
        // 得到的是文件通道
        FileChannel isChannel = fis.getChannel();
        FileChannel osChannel = fos.getChannel();
        // 分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (isChannel.read(buffer) > 0) {
            // 已经读取了数据 ，把缓冲区的模式切换成可读模式
            buffer.flip();
            // 把数据写出到
            osChannel.write(buffer); // 将buffer缓冲区中的数据写入到osChannel中
            // 必须先清空缓冲然后再写入数据到缓冲区
            buffer.clear();
        }
        isChannel.close();
        osChannel.close();
        System.out.println("复制完成！");
    }

    public static void transferFrom() throws Exception {

        // 1、字节输入管道
        FileInputStream is = new FileInputStream("/Users/lijunhao/Desktop/temp/Aurora-4k.jpg");
        FileChannel isChannel = is.getChannel();
        // 2、字节输出流管道
        FileOutputStream fos = new FileOutputStream("/Users/lijunhao/Desktop/temp/Aurora-4knew3.jpg");
        FileChannel osChannel = fos.getChannel();
        // 3、复制
        osChannel.transferFrom(isChannel, isChannel.position(), isChannel.size());
        isChannel.close();
        osChannel.close();
    }

    public static void transferTo() throws Exception {

        // 1、字节输入管道
        FileInputStream is = new FileInputStream("/Users/lijunhao/Desktop/temp/Aurora-4k.jpg");
        FileChannel isChannel = is.getChannel();
        // 2、字节输出流管道
        FileOutputStream fos = new FileOutputStream("/Users/lijunhao/Desktop/temp/Aurora-4knew4.jpg");
        FileChannel osChannel = fos.getChannel();
        // 3、复制
        isChannel.transferTo(isChannel.position(), isChannel.size(), osChannel);
        isChannel.close();
        osChannel.close();
    }

    public static void scatterAndGather() throws IOException {

        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");
        // 1. 获取通道
        FileChannel channel1 = raf1.getChannel();

        // 2. 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        // 3. 分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);

        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("-----------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        // 4. 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();

        channel2.write(bufs);
    }
}
