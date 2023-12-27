package com.wst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerReaderThread extends Thread{
    private Socket socket;

    public ServerReaderThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            //1. 从socket中获取当前客户端的输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while((msg = br.readLine()) != null){
                //2. 服务端接受到了客户端的消息之后，需要推送给当前所有在线socket
                sendMegToAllClient(msg, socket);
            }
        }catch (Exception e){
            System.out.println("当前有人下线！");

            //从在线socket集合中移除本socket
            Server.allSocketOnline.remove(socket);
        }
    }

    /**
     * 把当前客户端发送来的消息推送给全部在线的socket
     * @param msg
     */
    private void sendMegToAllClient(String msg, Socket socket) throws IOException {
        for (Socket sk : Server.allSocketOnline) {
            if(sk != socket){
                PrintStream ps = new PrintStream(sk.getOutputStream());
                ps.println(msg);
                ps.flush();
            }

        }
    }
}
