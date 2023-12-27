package com.wst.four;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try{
            //1.请求与服务端的Socket对象连接
            Socket socket = new Socket("127.0.0.1", 9999);

            //2.得到一个打印流
            PrintStream ps = new PrintStream(socket.getOutputStream());

            //3.使用循环不断给服务器发送数据
            Scanner sc = new Scanner(System.in);
            while(true){
                System.out.print("请输入：");
                String msg = sc.nextLine();
                ps.println(msg);
                ps.flush();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
