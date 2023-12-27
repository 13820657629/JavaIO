package com.wst.file;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标：服务点开发，可以实现接受客户端的任意类型文件，并保存到服务端磁盘。
 */

public class Server {
    public static void main(String[] args) {
        try{
            ServerSocket ss = new ServerSocket(9999);
            while(true){
                Socket socket = ss.accept();

                //交给一个独立的线程来处理与这个客户端通信需求
                new ServerReaderThread(socket).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
