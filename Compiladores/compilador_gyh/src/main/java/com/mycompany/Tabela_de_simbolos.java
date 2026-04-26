/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;
import java.util.HashMap;

/**
 *
 * @author Adm
 */
public class Tabela_de_simbolos {
    private static Tabela_de_simbolos instancia;
    public HashMap<String, Object> tabela;
            
    private Tabela_de_simbolos() {
        this.tabela = new HashMap<>();
    }
    public static synchronized Tabela_de_simbolos getTable() {
        if (instancia == null) {
            instancia = new Tabela_de_simbolos();
        }
        return instancia;
    }
    
}
