/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analisador_lexico;

/**
 *
 * @author a2614103
 */
public class Token {
    private final TipoToken padrao;
    private final String lexema;
    
    @Override
    public String toString(){
        return "<"+this.lexema+","+this.padrao+">";
    }
    
    public Token(String lexema, TipoToken padrao) {
        this.lexema=lexema;
        this.padrao=padrao;
    }
}
