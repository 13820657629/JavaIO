package com.wst.one;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 *  目标：客户端案例实现-基于NIO非阻塞通信
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //1. 获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));

        //2. 切换非阻塞模式
        sChannel.configureBlocking(false);

        //3. 分配指定缓冲区大小
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4. 发送数据给服务端
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.print("请输入：");
            String msg = sc.nextLine();
            buf.put(("客户端：" + msg).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

    }
}
