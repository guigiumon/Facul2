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
    private String nome_arquivo;
    private AnaliseLexica lex;
    
    private List<Token> tokens = new ArrayList<>();
    
    public Analisador_lexico(String nome_arquivo) {
        this.nome_arquivo = nome_arquivo;
        this.lex = new AnaliseLexica(nome_arquivo);
        Analise(nome_arquivo);
    }
    
    private void Analise(String nome_arquivo) {
        Token token;
        
        do {
            token = lex.NovoToken();
            if(token == null) break;
            System.out.println(token.toString());
            tokens.add(token);
        } while (true);
    }
    
    public List<Token> getTokens() {
        return this.tokens;
    }
}
