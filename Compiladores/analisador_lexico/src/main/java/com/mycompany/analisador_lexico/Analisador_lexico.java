/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.analisador_lexico;

/**
 *
 * @author a2614103
 */
public class Analisador_lexico {

    public static void main(String[] args) {
        AnaliseLexica lex=new AnaliseLexica("programa.gyh");
        
        Token t=lex.proxToken();
        
        while(t!=null){
            System.out.println(t.toString());
            t=lex.proxToken();
        }
        System.out.println("Ajeitar tudo nessa bomba!!!!!!!!!!!!");
        
    }
}
