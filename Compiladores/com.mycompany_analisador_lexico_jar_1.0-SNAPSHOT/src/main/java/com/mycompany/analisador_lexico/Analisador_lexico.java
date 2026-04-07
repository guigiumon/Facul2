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
        Token token;
        
        do {
            token = lex.NovoToken();
            if(token == null) break;
            System.out.print(token.toString());
        } while (true);
        
    }
}
