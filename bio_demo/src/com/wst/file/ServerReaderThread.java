package com.wst.file;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class ServerReaderThread extends Thread{
    private Socket socket;
    public ServerReaderThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            //1. 得到一个数据输入流，读取客户端发送过来的数据
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            //2. 读取客户端发送过来的文件类型
            String suffix = dis.readUTF();
            System.out.println("服务端已经成功接受到了文件类型：" + suffix);
            //3. 定义一个字节输出管道，负责把客户端发送来的文件数据写出去
            OutputStream os = new FileOutputStream("C:\\Users\\MI\\Desktop\\IO\\out\\" +
                    UUID.randomUUID().toString() + suffix);

            //4. 从数据输入流中读取文件数据，写到字节输出流中去
            byte[] buffer = new byte[1024];
            int len;
            while((len = dis.read(buffer)) > 0){
                os.write(buffer, 0, len);
            }
            os.close();
            System.out.println("服务端接受文件，保存成功");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
