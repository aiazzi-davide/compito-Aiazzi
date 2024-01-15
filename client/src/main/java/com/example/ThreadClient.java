package com.example;
import java.io.*;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


public class ThreadClient extends Thread {
    Socket s;
    BufferedReader inDalServer;
    public ThreadClient(Socket s) {
        this.s = s;
        try {
            inDalServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
            String risposta = inDalServer.readLine();
            if (risposta.contains("<list_pojo>")) {
                list_pojo lista = new list_pojo(unserializeList(risposta));
                System.out.println("SERVER: " + lista.toString() + '\n');
                continue;
            }

            System.out.println("SERVER: " + risposta + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        
    }
    public list_pojo unserializeList(String s){
        ObjectMapper Mapper = new ObjectMapper();
        try {
            System.out.println(s);
            list_pojo lista = Mapper.readValue(s, list_pojo.class);
            return lista;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Errore durante la deserializzazione della lista");
        }
        return null;
    }
}
