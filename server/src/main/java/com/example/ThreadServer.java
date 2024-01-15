package com.example;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.net.*;
import java.io.*;

public class ThreadServer extends Thread {

    //colori
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_YELLOW = "\u001B[33m";
    String ANSI_RESET = "\u001B[0m";

    //variabili
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String risposta = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    boolean exit = false;
    list_pojo lista = new list_pojo();

    public ThreadServer(Socket c) {
        this.client = c;
    }

    public void run() {
        String header = ANSI_RESET + "THREAD"+ currentThread().getName() + ": ";
        try {
            inDalClient = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());

            //messaggio di benvenuto
            outVersoClient.writeBytes(ANSI_GREEN + "Connessione effettuata, digita /exit per uscire o /list per visualizzare la lista" + ANSI_RESET + '\n');

            while (!exit) {
            
            outVersoClient.writeBytes(ANSI_BLUE + "inserisci una nota da memorizzare" + ANSI_RESET + '\n');

            //ricevo e stampo stringa
            stringaRicevuta = inDalClient.readLine();
            System.out.println(header + stringaRicevuta);
            
            //condizioni
            switch (stringaRicevuta) {

                case "/exit":
                    exit = true;
                    outVersoClient.writeBytes(ANSI_RED + "chiusura connessione" + ANSI_RESET + '\n');
                    break;

                case "/list":
                    if (lista.isEmpty()) {
                        outVersoClient.writeBytes(ANSI_YELLOW + "La lista e' vuota :(" + ANSI_RESET + '\n');
                    } else outVersoClient.writeBytes(serializeList(lista) + '\n');
                    break;

                default:
                    buildList(stringaRicevuta);
                    outVersoClient.writeBytes(ANSI_GREEN + "Nota Salvata" + ANSI_RESET + '\n');
                    break;
            }
        }

        //chiusura connessione
        client.close();
        System.out.println(ANSI_YELLOW + "chiusura connessione con il client " + client.getInetAddress() + " sulla porta " + client.getPort() + ANSI_RESET);
        }catch (SocketException e) {
            System.out.println(header+ ANSI_RED + ":  Connessione chiusa dal client!");
            System.exit(1);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(header+ ANSI_RED + ":  qualcosa è andato storto!");
            System.exit(1);
        }
    }

    public void buildList(String stringaRicevuta) {
        lista.add(stringaRicevuta);
    }
    public String serializeList(list_pojo lista) {
        String s1;
        try {
            System.out.println("Serializing list...");
            ObjectMapper Mapper = new ObjectMapper();
            s1 = Mapper.writeValueAsString(lista);
            Mapper.writeValue(new File("test.json"), lista);
            return s1;

        } catch (Exception i) {
            i.printStackTrace();
        }
        return "error";
    }
}
