/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;
import com.mycompany.analisador_lexico.Analisador_lexico;
import com.mycompany.analisador_sintatico.Analisador_sintatico;        


/**
 *
 * @author Adm
 */
public class Main {    
    public static void main(String[] args) {
        Analisador_lexico analise_lexica = new Analisador_lexico("programa1.gyh");
        analise_lexica.getTokens();
        
        Analisador_sintatico analise_sintatica = new Analisador_sintatico(analise_lexica.getTokens());
    }
}
