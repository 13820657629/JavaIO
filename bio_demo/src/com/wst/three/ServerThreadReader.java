package com.wst.three;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThreadReader extends Thread{

    private Socket socket;
    public ServerThreadReader(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
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
