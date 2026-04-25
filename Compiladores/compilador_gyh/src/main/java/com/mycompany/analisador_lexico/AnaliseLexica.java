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
    
    public char ignoraVazios( char c) {
        while (c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '#') {
            if (c == '#') {
                // Se for comentário, pula até o fim da linha
                while (c != '\n' && c != '\r' && (int)c != -1) {
                    c = (char) ldat.lerProximoCaractere();
                }
            }
            if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
                c = (char) ldat.lerProximoCaractere();
            }
        }
        return c;
    }
    
    public Token NovoToken(){
        Token token = null;
        
        if (this.look_forward != '#') {
            char aux = this.look_forward;
            look_forward='#';
            aux = ignoraVazios(aux);
            token = IdentificaToken(aux);
        } else {
            int caractere=ldat.lerProximoCaractere();
            char c=(char) caractere;
            c = ignoraVazios(c);
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
            while(Character.isLowerCase(c) || Character.isDigit(c)) {
                nome_var += Character.toString(c);
                c = (char) ldat.lerProximoCaractere();
            }
            
            if(!Character.isLowerCase(c) || !Character.isDigit(c)){
                this.look_forward = c;
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
                if((int)c == 65535) {
                    lexemaInexistente("\"");
                }
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
                        constroiLexema(c);
                        lexemaInexistente(this.lexema);
                    }
                } else {
                    constroiLexema(c);
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
                            constroiLexema(c);
                            lexemaInexistente(this.lexema);
                        }
                    } else {
                        constroiLexema(c);
                        lexemaInexistente(this.lexema);
                    }
                } else {
                    constroiLexema(c);
                    lexemaInexistente(this.lexema);
                }
                break;
            
            case 'I'://INT INI e IMPRIMIR
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                switch(c) {
                    case 'N':
                        constroiLexema(c);
                        c = (char) ldat.lerProximoCaractere();
                        switch(c) {
                            case 'T' -> { 
                                return new Token("INT", TipoToken.PCInt); // ---- PCInt -----
                            }
                            case 'I' -> {
                                return new Token("INI", TipoToken.PCIni); // ---- PCIni -----
                            }
                            default -> {
                                constroiLexema(c);
                                lexemaInexistente(this.lexema);
                            }
                        }

                    case 'M': 
                        constroiLexema(c);
                        c = (char) ldat.lerProximoCaractere();
                        if(c == 'P') {
                            constroiLexema(c);
                            c = (char) ldat.lerProximoCaractere();
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
                                                constroiLexema(c);
                                                lexemaInexistente(this.lexema);
                                            }
                                        } else {
                                            constroiLexema(c);
                                            lexemaInexistente(this.lexema);
                                        }
                                    } else {
                                        constroiLexema(c);
                                        lexemaInexistente(this.lexema);
                                    }
                                } else {
                                    constroiLexema(c);
                                    lexemaInexistente(this.lexema);
                                }
                            } else {
                                constroiLexema(c);
                                lexemaInexistente(this.lexema);
                            }
                        } else {
                            constroiLexema(c);
                            lexemaInexistente(this.lexema);
                        }
                    default: 
                        constroiLexema(c);
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
                        constroiLexema(c);
                        lexemaInexistente(this.lexema);
                    }
                } else {
                    constroiLexema(c);
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
                            constroiLexema(c);
                            lexemaInexistente(this.lexema);
                        } 
                    } else {
                        constroiLexema(c);
                        lexemaInexistente(this.lexema);
                    }
                } else {
                    constroiLexema(c);                    
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
                    if (c != 'N') {
                        this.look_forward = '#';
                        return new Token("SE", TipoToken.PCSe);
                    } // ---- PCSe -----
                    else {
                        constroiLexema(c);
                        c = (char) ldat.lerProximoCaractere();
                        if(c == 'A') {
                            constroiLexema(c);
                            c = (char) ldat.lerProximoCaractere();
                            if(c == 'O') {
                                return new Token("SENAO", TipoToken.PCSenao); // ---- PCSenao -----
                            } else {
                                constroiLexema(c);
                                lexemaInexistente(this.lexema);
                            }
                        } else {
                            constroiLexema(c);
                            lexemaInexistente(this.lexema);
                        }
                    }
                } else {
                    constroiLexema(c);
                    lexemaInexistente(this.lexema);
                }
                break;
            
            case 'E': // E, ENTAO, ENQTO
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if(c != 'N') {
                    this.look_forward = '#';
                    return new Token("E", TipoToken.OpBoolE);
                } // ---- OpBoolE -----
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
                                    this.look_forward = '#';
                                    return new Token("ENQTO", TipoToken.PCEnqto); // ---- PCEnqto -----
                                } else {
                                    constroiLexema(c);
                                    lexemaInexistente(this.lexema);
                                }
                            } else {
                                constroiLexema(c);
                                lexemaInexistente(this.lexema);
                            }
                        }
                        case 'T' -> {
                            constroiLexema(c);
                            c = (char) ldat.lerProximoCaractere();
                            if(c == 'A') {
                                constroiLexema(c);
                                c = (char) ldat.lerProximoCaractere();
                                if(c == 'O') {
                                    this.look_forward = '#';
                                    return new Token("ENTAO", TipoToken.PCEntao); // ---- PCEntao -----
                                } else {
                                    constroiLexema(c);
                                    lexemaInexistente(this.lexema);
                                }
                            } else {
                                constroiLexema(c);
                                lexemaInexistente(this.lexema);
                            }
                        }
                        default -> {
                            constroiLexema(c);
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
                        constroiLexema(c);
                        lexemaInexistente(this.lexema);
                    }
                } else {
                    constroiLexema(c);
                    lexemaInexistente(this.lexema);
                }
                break;
                
            case 'O': //OU
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == 'U') { 
                    return new Token("OU", TipoToken.OpBoolOu);// ---- OpBoolOu -----
                } else {
                    constroiLexema(c);
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
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if (c == '=') {
                    this.look_forward = '#';
                    return new Token(":=", TipoToken.Atrib);
                } // ---- Atrib -----
                return new Token(":", TipoToken.Delim); // ---- Delim -----
            
            case '<': //<= <
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if (c == '=') {
                    this.look_forward = '#';
                    return new Token("<=", TipoToken.OpRelMenorIgual);
                } // ---- OpRelMenorIgual -----
                return new Token("<", TipoToken.OpRelMenor);  // ---- OpRelMenor -----
            
            case '>':  //> >=
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                this.look_forward = c;
                if (c == '=') {
                    this.look_forward = '#';
                    return new Token(">=", TipoToken.OpRelMaiorIgual);
                } // ---- OpRelMaiorIgual -----
                this.look_forward = '#';
                return new Token(">", TipoToken.OpRelMaior);  // ---- OpRelMaior -----
            
            case '=': //  ==
                this.look_forward = c;
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == '=') {
                    this.look_forward = '#';
                    return new Token("==", TipoToken.OpRelIgual);// ---- OpRelIgual -----
                } else {
                    constroiLexema(c);
                    lexemaInexistente(this.lexema);
                }
                
            case '!': //  !=
                constroiLexema(c);
                c = (char) ldat.lerProximoCaractere();
                if (c == '=') {
                    return new Token("!=", TipoToken.OpRelDif); // ---- OpRelDif -----
                } else {
                    constroiLexema(c);
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
