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
    private char look_forward = '#'; // para casos de <,>,:
    public AnaliseLexica(String nome){
        ldat=new LeitorArquivo(nome);
    }
    
    public Token NovoToken(){
        Token token = null;
        
        if (this.look_forward != '#') {
            char aux = this.look_forward;
            look_forward='#';
            token = IdentificaToken(aux);
        } else {
            int caractere=ldat.lerProximoCaractere();
            char c=(char) caractere;

            token = IdentificaToken(c);
        }
        return token;
    }
    
    public Token IdentificaToken(char c) {
        
        if((int) c == -1) {
            return null;
        }
        
        if(c == '\r' || c == '\n') {
            if(c == '\r') {
                c = (char) ldat.lerProximoCaractere();
            }
            if(c == '\n') {
                c = (char) ldat.lerProximoCaractere();
            }
        }
        
        //nome de variável
        if (Character.isLowerCase(c)) {
            String nome_var = "";
            while(Character.isLowerCase(c)) {
                nome_var += Character.toString(c);
                c = (char) ldat.lerProximoCaractere();
            }
            return new Token(nome_var, TipoToken.Var); // ---- Var -----
        }
           
        //DEC
        
        switch(c) {
            case 'D': //DEC
                c = (char) ldat.lerProximoCaractere();
                if (c == 'E') {
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'C') return new Token("DEC", TipoToken.PCDec); // ---- PCDec -----
                }
                break;
            
            case 'P': //PROG
                c = (char) ldat.lerProximoCaractere();
                if (c == 'R') {
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'O') {
                        c = (char) ldat.lerProximoCaractere();
                        if (c == 'G') return new Token("PROG", TipoToken.PCProg); // ---- PCProg -----
                    }
                }
                break;
            
            case 'I'://INT INI e IMPRIMIR
                c = (char) ldat.lerProximoCaractere();
                switch(c) {
                    case 'N':
                        switch(c) {
                            case 'T' -> { 
                                return new Token("INT", TipoToken.PCInt); // ---- PCInt -----
                            }
                            case 'I' -> {
                                return new Token("INI", TipoToken.PCIni); // ---- PCIni -----
                            }
                        }

                    case 'M': 
                        c = (char) ldat.lerProximoCaractere();
                        if(c == 'P') {
                            if(c == 'R') {
                                c = (char) ldat.lerProximoCaractere();
                                if(c == 'I') {
                                    c = (char) ldat.lerProximoCaractere();
                                    if(c == 'M') {
                                    c = (char) ldat.lerProximoCaractere();
                                        if(c == 'I') {
                                            c = (char) ldat.lerProximoCaractere();
                                                if(c == 'R') {
                                                    return new Token("IMPRIMIR", TipoToken.PCImprimir); // ---- PCImprimir -----
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }
                break;
            
            case 'L': //LER
                c = (char) ldat.lerProximoCaractere();
                if (c == 'E') {
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'R') return new Token("LER", TipoToken.PCLer); // ---- PCLer -----
                }
                break;
            
            case 'R': //REAL
                c = (char) ldat.lerProximoCaractere();
                if (c == 'E') {
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'A') {
                        c = (char) ldat.lerProximoCaractere();
                        if (c == 'L') return new Token("REAL", TipoToken.PCReal); // ---- PCReal -----
                    }
                }
                break;
            
            case 'S': // SE, SENAO
                c = (char) ldat.lerProximoCaractere();
                if(c == 'E') {
                    c = (char) ldat.lerProximoCaractere();
                    this.look_forward = c;
                    if (c == 'N') {
                        c = (char) ldat.lerProximoCaractere();
                        if(c == 'A') {
                            c = (char) ldat.lerProximoCaractere();
                            if(c == 'O') {
                                return new Token("SENAO", TipoToken.PCSenao); // ---- PCSenao -----
                            }
                        }
                    } else {
                        return new Token("SE", TipoToken.PCSe); // ---- PCSe -----
                    }
                }
                break;
            
            case 'E': // E, ENTAO, ENQTO
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if(c == 'N') {
                    c = (char) ldat.lerProximoCaractere();
                    switch(c){
                        case 'Q' -> {
                            c = (char) ldat.lerProximoCaractere();
                            if(c == 'T') {
                                c = (char) ldat.lerProximoCaractere();
                                if(c == 'O') {
                                    return new Token("ENQTO", TipoToken.PCEnqto); // ---- PCEnqto -----
                                }
                            }
                        }
                        case 'T' -> {
                            if(c == 'A') {
                                c = (char) ldat.lerProximoCaractere();
                                if(c == 'O') {
                                    return new Token("ENTAO", TipoToken.PCEntao); // ---- PCEntao -----
                                }
                            }
                        }
                    }
                } else {
                    return new Token("E", TipoToken.OpBoolE); // ---- OpBoolE -----
                }
                break;
            
            case 'F': //FIM
                c = (char) ldat.lerProximoCaractere();
                if (c == 'I') {
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'M') return new Token("FIM", TipoToken.PCFim); // ---- PCFim -----
                }
                break;
                
            case 'O': //OU
                c = (char) ldat.lerProximoCaractere();
                if (c == 'U') return new Token("OU", TipoToken.OpBoolOu); // ---- OpBoolOu -----
                break;
            
            case '+': //+
                return new Token("+", TipoToken.OpAritSoma); // ---- OpAritSoma -----
                
            case '-': //-
                return new Token("-", TipoToken.OpAritSub); // ---- OpAritSub -----
                
            case '*': //*
                return new Token("*", TipoToken.OpAritMult); // ---- OpAritMult -----
            
            case '/': // /
                return new Token("/", TipoToken.OpAritDiv); // ---- OpAritDiv -----
                
            case ':': //: e :=
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if (c == '=') return new Token(":=", TipoToken.Atrib); // ---- Atrib -----
                else {
                    return new Token(":", TipoToken.Delim); // ---- Delim -----
                }
            
            case '<': //<= <
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if (c == '=') return new Token("<=", TipoToken.OpRelMenorIgual); // ---- OpRelMenorIgual -----
                return new Token("<", TipoToken.OpRelMenor);  // ---- OpRelMenor -----
            
            case '>':  //> >=
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if (c == '=') return new Token(">=", TipoToken.OpRelMaiorIgual); // ---- OpRelMaiorIgual -----
                return new Token(">", TipoToken.OpRelMaior);  // ---- OpRelMaior -----
            
            case '=': //  ==
                this.look_forward = c;
                c = (char) ldat.lerProximoCaractere();
                if (c == '=') return new Token("==", TipoToken.OpRelIgual); // ---- OpRelIgual -----
                
            case '!': //  !=
                c = (char) ldat.lerProximoCaractere();
                if (c == '=') {
                    return new Token("!=", TipoToken.OpRelDif); // ---- OpRelDif -----
                }
                
            case '(': // (
                return new Token("(", TipoToken.AbrePar); // ---- AbrePar -----
            
            case ')': // )
                return new Token(")", TipoToken.FechaPar); // ---- FechaPar -----
            
            default:
                return null;
        }
        
        //numeros
        if (Character.isDigit(c)) {
            String numero = "";
            while(Character.isDigit(c) || c == '.') {
                numero += Character.toString(c);
                
                c = (char) ldat.lerProximoCaractere();
            }
            if(numero.contains(String.valueOf('.'))){
                return new Token(numero, TipoToken.NumReal); // ---- NumReal -----
            }
            return new Token(numero, TipoToken.NumInt); // ---- NumInt -----
        }
        
        // Cadeia
        else if(c =='"') {
            String texto = "";
            c = (char) ldat.lerProximoCaractere();
            while(c != '"') {
                texto += Character.toString(c);
                c = (char) ldat.lerProximoCaractere();
            }
            return new Token(texto, TipoToken.Cadeia); // ---- Cadeia -----
        }

        return null;
    }
}
