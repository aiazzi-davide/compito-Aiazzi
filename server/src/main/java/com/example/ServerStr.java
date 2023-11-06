package com.example;
import java.net.*;
import java.io.*;

public class ServerStr
{
    Socket client = null;
    ServerSocket server = null;
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_RESET = "\u001B[0m";
    String ANSI_YELLOW = "\u001B[33m";

    

    public void start(int port) {
        System.out.println(ANSI_GREEN + "SERVER in esecuzione ..." + ANSI_RESET);
        try {
            server = new ServerSocket(port);
            while (true) {
                client = server.accept();
                System.out.println(ANSI_GREEN + "connesso con il client " + client.getInetAddress() + " sulla porta " + client.getPort() + ANSI_RESET);
                Thread t = new ThreadServer(client);
                t.start();
            }
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Errore durante l'istanza del server !");
            e.printStackTrace();
            System.exit(1);
            }
        }
}
