package com.wst.four;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerRunnableTarget implements Runnable{
    private Socket socket;

    public ServerRunnableTarget(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        // 处理接收到的客户端Socket通信需求

        try{
            //4. 从socket管道中得到一个字节输入流
            InputStream is = socket.getInputStream();

            //5. 使用缓冲字符输入流包装字节输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
            while((msg = br.readLine()) != null){
                System.out.println("服务端接受消息：" + msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
