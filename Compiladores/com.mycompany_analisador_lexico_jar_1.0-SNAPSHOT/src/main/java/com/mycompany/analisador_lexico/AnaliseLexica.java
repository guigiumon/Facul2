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
    private String lexema = "";
    public AnaliseLexica(String nome){
        ldat=new LeitorArquivo(nome);
    }
    
    public void constroiLexema(char letra){
        this.lexema = this.lexema + letra;
    }
    
    public void lexemaInexistente(String palavra) {
        throw new Error("Palavra " + palavra + " não pertence a linguagem!!\n");
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
        this.lexema = "";
        
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
        
        //DEC
        
        switch(c) {
            case 'D': //DEC
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == 'E') {
                    constroiLexema(c);
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'C') {
                        return new Token("DEC", TipoToken.PCDec);
                    }  else {// ---- PCDec -----
                        lexemaInexistente(this.lexema);
                    }
                } else {
                    lexemaInexistente(this.lexema);
                }
                break;
            
            case 'P': //PROG
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == 'R') {
                    constroiLexema(c);
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'O') {
                        constroiLexema(c);
                        c = (char) ldat.lerProximoCaractere();
                        if (c == 'G') {
                            return new Token("PROG", TipoToken.PCProg); // ---- PCProg -----
                        } else {
                            lexemaInexistente(this.lexema);
                        }
                    } else {
                        lexemaInexistente(this.lexema);
                    }
                } else {
                    lexemaInexistente(this.lexema);
                }
                break;
            
            case 'I'://INT INI e IMPRIMIR
                constroiLexema(c);
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
                            default -> {
                                lexemaInexistente(this.lexema);
                            }
                        }

                    case 'M': 
                        constroiLexema(c);
                        c = (char) ldat.lerProximoCaractere();
                        if(c == 'P') {
                            if(c == 'R') {
                                constroiLexema(c);
                                c = (char) ldat.lerProximoCaractere();
                                if(c == 'I') {
                                    constroiLexema(c);
                                    c = (char) ldat.lerProximoCaractere();
                                    if(c == 'M') {
                                        constroiLexema(c);
                                        c = (char) ldat.lerProximoCaractere();
                                        if(c == 'I') {
                                            constroiLexema(c);
                                            c = (char) ldat.lerProximoCaractere();
                                            if(c == 'R') {
                                                return new Token("IMPRIMIR", TipoToken.PCImprimir); // ---- PCImprimir -----
                                            } else {
                                                    lexemaInexistente(this.lexema);
                                                }
                                        } else {
                                                lexemaInexistente(this.lexema);
                                            }
                                    } else {
                                        lexemaInexistente(this.lexema);
                                    }
                                } else {
                                    lexemaInexistente(this.lexema);
                                }
                            } else {
                                lexemaInexistente(this.lexema);
                            }
                        } else {
                            lexemaInexistente(this.lexema);
                        }
                    default: 
                        lexemaInexistente(this.lexema);
                }
                break;
            
            case 'L': //LER
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == 'E') {
                    constroiLexema(c);
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'R') {
                        return new Token("LER", TipoToken.PCLer); // ---- PCLer -----
                    } else {
                        lexemaInexistente(this.lexema);
                    }
                } else {
                    lexemaInexistente(this.lexema);
                }
                break;
            
            case 'R': //REAL
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == 'E') {
                    constroiLexema(c);
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'A') {
                        constroiLexema(c);
                        c = (char) ldat.lerProximoCaractere();
                        if (c == 'L') {
                            return new Token("REAL", TipoToken.PCReal); // ---- PCReal -----
                        } else {
                            lexemaInexistente(this.lexema);
                        } 
                    } else {
                        lexemaInexistente(this.lexema);
                    }
                } else {
                    lexemaInexistente(this.lexema);
                }
                break;
            
            case 'S': // SE, SENAO
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if(c == 'E') {
                    constroiLexema(c);
                    c = (char) ldat.lerProximoCaractere();
                    this.look_forward = c;
                    if (c != 'N') return new Token("SE", TipoToken.PCSe); // ---- PCSe -----
                    else {
                        constroiLexema(c);
                        c = (char) ldat.lerProximoCaractere();
                        if(c == 'A') {
                            constroiLexema(c);
                            c = (char) ldat.lerProximoCaractere();
                            if(c == 'O') {
                                return new Token("SENAO", TipoToken.PCSenao); // ---- PCSenao -----
                            } else {
                                lexemaInexistente(this.lexema);
                            }
                        } else {
                            lexemaInexistente(this.lexema);
                        }
                    }
                } else {
                    lexemaInexistente(this.lexema);
                }
                break;
            
            case 'E': // E, ENTAO, ENQTO
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if(c != 'N') return new Token("E", TipoToken.OpBoolE); // ---- OpBoolE -----
                else {
                    constroiLexema(c);
                    c = (char) ldat.lerProximoCaractere();
                    switch(c){
                        case 'Q' -> {
                            constroiLexema(c);
                            c = (char) ldat.lerProximoCaractere();
                            if(c == 'T') {
                                constroiLexema(c);
                                c = (char) ldat.lerProximoCaractere();
                                if(c == 'O') {
                                    return new Token("ENQTO", TipoToken.PCEnqto); // ---- PCEnqto -----
                                } else {
                                    lexemaInexistente(this.lexema);
                                }
                            } else {
                                lexemaInexistente(this.lexema);
                            }
                        }
                        case 'T' -> {
                            if(c == 'A') {
                                constroiLexema(c);
                                c = (char) ldat.lerProximoCaractere();
                                if(c == 'O') {
                                    return new Token("ENTAO", TipoToken.PCEntao); // ---- PCEntao -----
                                } else {
                                    lexemaInexistente(this.lexema);
                                }
                            } else {
                                lexemaInexistente(this.lexema);
                            }
                        }
                        default -> {
                            lexemaInexistente(this.lexema);
                        }
                    }
                } 
                break;
            
            case 'F': //FIM
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == 'I') {
                    constroiLexema(c);
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'M') {
                        return new Token("FIM", TipoToken.PCFim); // ---- PCFim -----
                    }  else {
                        lexemaInexistente(this.lexema);
                    }
                } else {
                    lexemaInexistente(this.lexema);
                }
                break;
                
            case 'O': //OU
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == 'U') { 
                    return new Token("OU", TipoToken.OpBoolOu);// ---- OpBoolOu -----
                } else {
                    lexemaInexistente(this.lexema);
                }
                
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
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if (c != '=') {
                    lexemaInexistente(this.lexema);
                } else if (c == '=') return new Token(":=", TipoToken.Atrib); // ---- Atrib -----
                return new Token(":", TipoToken.Delim); // ---- Delim -----
            
            case '<': //<= <
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if (c != '=') {
                    lexemaInexistente(this.lexema);
                } else if (c == '=') return new Token("<=", TipoToken.OpRelMenorIgual); // ---- OpRelMenorIgual -----
                return new Token("<", TipoToken.OpRelMenor);  // ---- OpRelMenor -----
            
            case '>':  //> >=
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if (c != '=') {
                    lexemaInexistente(this.lexema);
                } else if (c == '=') return new Token(">=", TipoToken.OpRelMaiorIgual); // ---- OpRelMaiorIgual -----
                return new Token(">", TipoToken.OpRelMaior);  // ---- OpRelMaior -----
            
            case '=': //  ==
                this.look_forward = c;
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == '=') {
                    return new Token("==", TipoToken.OpRelIgual);// ---- OpRelIgual -----
                } else {
                    lexemaInexistente(this.lexema);
                }
                
            case '!': //  !=
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == '=') {
                    return new Token("!=", TipoToken.OpRelDif); // ---- OpRelDif -----
                } else {
                    lexemaInexistente(this.lexema);
                }
                
            case '(': // (
                return new Token("(", TipoToken.AbrePar); // ---- AbrePar -----
            
            case ')': // )
                return new Token(")", TipoToken.FechaPar); // ---- FechaPar -----
            
            default:
                return null;
        }
        
        return null;
    }
}
