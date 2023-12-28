package com.wst;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 服务端群聊系统实现
 */
public class Server {
    //1. 定义一些成员属性：选择器、服务端通道、端口
    private Selector selector;
    private ServerSocketChannel ssChannel;
    private static final int PORT = 9999;

    //2. 定义初始化构造器
    public Server(){
        try{
            //创建选择器对象
            selector = Selector.open();

            //获取通道
            ssChannel = ServerSocketChannel.open();

            //绑定客户端连接的端口
            ssChannel.bind(new InetSocketAddress(PORT));

            //设置非阻塞通信模式
            ssChannel.configureBlocking(false);

            //把通道注册到选择器上，并且开始指定接受的连接事件
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 开始监听
     */
    private void listen() {
        try{
            while(selector.select() > 0){
                // 获取选择器中的所有注册通道的就绪事件
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                //遍历事件
                while(it.hasNext()){
                    //提取事件
                    SelectionKey sk = it.next();

                    //判断事件的类型
                    if(sk.isAcceptable()){
                        //客户端接入请求，获取当前客户端通道
                        SocketChannel schannel = ssChannel.accept();

                        //注册非阻塞模式
                        schannel.configureBlocking(false);

                        //客户端通道注册给选择器，监听读数据的事件
                        schannel.register(selector, SelectionKey.OP_READ);
                    }
                    else if(sk.isReadable()){
                        //处理这个客户端的消息，接收消息，实现转发逻辑
                        readClientData(sk);
                    }
                    it.remove(); //处理完毕之后需要移除当前事件

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 接受当前客户端通道的信息，转发给其他全部客户端通道
     * @param sk
     */
    private void readClientData(SelectionKey sk) {
        SocketChannel sChannel = null;
        try{
            //直接得到当前客户端通道
            sChannel = (SocketChannel) sk.channel();

            //创建缓冲区对象开始接受客户端通道的数据
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = sChannel.read(buffer);
            if(count > 0){
                buffer.flip();
                //提取读取到的信息
                String msg = new String(buffer.array(), 0, buffer.remaining());
                System.out.println("接受到客户端消息：" + msg);

                //把这个消息推送给全部客户端
                sendMsgToAllClient(msg, sChannel);
            }
        }catch (Exception e){
            try{
                System.out.println("有人离线" + sChannel.getRemoteAddress());
                // 当前客户端离线
                sk.cancel();    //取消注册
                sChannel.close();
            }catch (Exception ioException){
                ioException.printStackTrace();
            }

        }
    }

    /**
     * 把当前客户端的消息数据推送给当前全部在线注册的channel
     * @param msg
     * @param sChannel
     */
    private void sendMsgToAllClient(String msg, SocketChannel sChannel) throws IOException {
        System.out.println("服务端开始转发消息，当前处理的线程：" + Thread.currentThread().getName());
        for(SelectionKey key : selector.keys()){
            Channel channel = key.channel();

            //排除自己
            if(channel instanceof SocketChannel && channel != sChannel){
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel)channel).write(buffer);
            }
        }
    }


    public static void main(String[] args) {
        //创建服务端对象
        Server server = new Server();

        //开始监听客户端的各种消息事件：连接、群聊消息、离线消息
        server.listen();
    }


}
