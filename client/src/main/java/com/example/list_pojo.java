package com.example;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class list_pojo {
    ArrayList<String> lista = new ArrayList<String>();
    list_pojo() {
    }
    list_pojo(list_pojo l) {
        this.lista = l.getLista();
    }

    public ArrayList<String> getLista() {
        return lista;
    }
    public void setLista(ArrayList<String> lista) {
        this.lista = lista;
    }
    public void add(String nota) {
        lista.add(nota);
    }
    public void remove(int index) {
        lista.remove(index);
    }
    public String get(int index) {
        return lista.get(index);
    }
    public boolean isEmpty() {
        return lista.isEmpty();
    }

    @Override
    public String toString() {
        String s = "";
        for (String nota : lista) {
            s += nota + '\n';
        }
        return s;
    }
}
