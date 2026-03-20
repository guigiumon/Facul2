/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analisador_lexico;

/**
 *
 * @author a2614103
 */
public class AnaliseLexica {
    public LeitorArquivo ldat;
    
    public AnaliseLexica(String nome){
        ldat=new LeitorArquivo(nome);
    }
    
    public Token proxToken(){
        int caractere=ldat.lerProximoCaractere();
        
        while(caractere >=0) {
            char c=(char) caractere;
            switch(c) {
                case'+': return new Token("+",TipoToken.OpAritSoma);
                case'-': return new Token("+",TipoToken.OpAritSub);
                //melhorar
            }
            
            caractere=ldat.lerProximoCaractere();
        }
        return null;
    }
}
