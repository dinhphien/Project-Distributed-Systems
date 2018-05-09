/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * h
 *
 * @author T430
 */
public class Server {

    static int count = 1;

    public static synchronized void upCount() {
        count++;
    }

    public static synchronized void downCount() {
        count--;
    }

    public static int getCount() {
        return count;
    }

    public static void main(String args[]) throws IOException {
        ServerSocket listener = null;
        Coodinator coo = new Coodinator();
        ClientHandler.setCoo(coo);
        try {
            System.out.println("abc");
            listener = new ServerSocket(8333);
        } catch (IOException e) {
            System.exit(1);
        }
        ExecutorService executor = Executors.newFixedThreadPool(5);
        while (true) {
            Socket s = null;
            try {
                s = listener.accept();
                //          System.out.println(s.getChannel().getRemoteAddress());
                System.out.println("Có " + count + " người đang kết nối");
                System.out.println("Creat a new thread");
                Thread t = new ClientHandler(s);
                t.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
