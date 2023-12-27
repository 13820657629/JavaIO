package com.wst;

import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try{
            //1.请求与服务端的Socket对象连接
            Socket socket = new Socket("127.0.0.1", 9999);

            new ClientWriterThread(socket).start();

            new ClientReaderThread(socket).start();



        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
