package com.wst;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientWriterThread extends Thread{
    private Socket socket;

    ClientWriterThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintStream ps = new PrintStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            while(true){
                String msg = sc.nextLine();
                ps.println(msg);
                ps.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
