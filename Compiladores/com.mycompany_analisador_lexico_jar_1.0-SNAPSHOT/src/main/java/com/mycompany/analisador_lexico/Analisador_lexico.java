/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.analisador_lexico;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author a2614103
 */
public class Analisador_lexico {
    
    private static List<Token> tokens = new ArrayList<>();
    
    public static void main(String[] args) {
        AnaliseLexica lex=new AnaliseLexica("programa11.gyh");
        Token token;
        
        
        do {
            token = lex.NovoToken();
            if(token == null) break;
            System.out.println(token.toString());
            tokens.add(token);
        } while (true);
        
    }
}
