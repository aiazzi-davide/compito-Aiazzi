package com.example;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientStr {

    String ANSI_GREEN = "\u001B[32m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_RESET = "\u001B[0m";
    String ANSI_YELLOW = "\u001B[33m";


    Socket miosocket;
    Scanner input;
    String stringaUtente;
    String risposta;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    boolean exit = false;

    public void connetti(String nomeServer, int portaServer) {
        System.out.println(ANSI_GREEN + "CLIENT in esecuzione ..." + ANSI_RESET);
        try {

            input = new Scanner(System.in);
            miosocket = new Socket(nomeServer, portaServer);

            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Host sconosciuto");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(ANSI_RED + "something went wrong, closing client...");
            System.exit(1);
        }
    }
    public void comunica() {
        try {
            //ricevo stringa iniziale
            System.out.println(inDalServer.readLine());
            ThreadClient t = new ThreadClient(miosocket);
            t.start();

            while (!exit) {

                //scrivo la risposta al server
                stringaUtente = input.next();
                outVersoServer.writeBytes(stringaUtente + '\n');

                //controllo exit
                if (stringaUtente.contains("/exit")) {
                    exit = true;
                }
                //stampa risposta
                

            }
            miosocket.close();
            System.out.println(ANSI_YELLOW + "Connessione chiusa");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(ANSI_RED + "Errore durante la comunicazione col server!");
            System.exit(1);
        }
    } 
}
