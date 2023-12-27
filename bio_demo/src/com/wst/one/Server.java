package com.wst.one;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标：客户端发送消息，服务端接受消息
 */
public class Server {
    public static void main(String[] args) {
        try{
            System.out.println("==服务端启动==");
            //1. 定义一个ServerSocket对象进行服务端的端口注册
            ServerSocket ss = new ServerSocket(9999);

            //2. 监听客户端的Socket链接请求
            Socket sock = ss.accept();

            //3. 从socket管道中得到一个字节输入流对象
            InputStream is = sock.getInputStream();

            //4. 把字节输入流包装成一个缓冲字符输入流
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));

            String msg;
            if((msg = bf.readLine()) != null){
                System.out.println("服务端接收到：" + msg);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
