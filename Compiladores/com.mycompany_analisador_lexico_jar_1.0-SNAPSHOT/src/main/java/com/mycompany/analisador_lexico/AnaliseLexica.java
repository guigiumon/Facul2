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
    
    public void NovoToken(){
        if (this.look_forward != '#') {
            char aux = this.look_forward;
            look_forward='#';
            IdentificaToken(aux);
        }
        
        int caractere=ldat.lerProximoCaractere();
        
        while(caractere >=0) {
            char c=(char) caractere;
            
            IdentificaToken(c);
            
            caractere=ldat.lerProximoCaractere();
        }
    }
    
    public Token IdentificaToken(char c) {
        
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
        if (c == 'D') {
            c = (char) ldat.lerProximoCaractere();
            if (c == 'E') {
                c = (char) ldat.lerProximoCaractere();
                if (c == 'C') return new Token("DEC", TipoToken.PCDec); // ---- PCDec -----
            }
        } 
        
        //PROG
        else if (c == 'P') {
            c = (char) ldat.lerProximoCaractere();
            if (c == 'R') {
                c = (char) ldat.lerProximoCaractere();
                if (c == 'O') {
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'G') return new Token("PROG", TipoToken.PCProg); // ---- PCProg -----
                }
            }
        }
        
        //INT INI e IMPRIMIR
        else if(c == 'I') {
            c = (char) ldat.lerProximoCaractere();
            if(c == 'N') {
                c = (char) ldat.lerProximoCaractere();
                if(c == 'T') {
                    return new Token("INT", TipoToken.PCInt); // ---- PCInt -----
                } else if(c == 'I') {
                    return new Token("INI", TipoToken.PCIni); // ---- PCIni -----
                }
            } else if(c == 'M') {
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
        }

        //LER
        else if (c == 'L') {
            c = (char) ldat.lerProximoCaractere();
            if (c == 'E') {
                c = (char) ldat.lerProximoCaractere();
                if (c == 'R') return new Token("LER", TipoToken.PCLer); // ---- PCLer -----
            }
        } 
        
        //REAL
        else if (c == 'R') {
            c = (char) ldat.lerProximoCaractere();
            if (c == 'E') {
                c = (char) ldat.lerProximoCaractere();
                if (c == 'A') {
                    c = (char) ldat.lerProximoCaractere();
                    if (c == 'L') return new Token("REAL", TipoToken.PCReal); // ---- PCReal -----
                }
            }
        }
        
        //SE. SENAO
        else if (c == 'S') {
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
        }
        
        // E, ENTAO, ENQTO
        else if (c == 'E') {
            c = (char) ldat.lerProximoCaractere();
            this.look_forward = c;
            if(c == 'N') {
                c = (char) ldat.lerProximoCaractere();
                if (c == 'Q') {
                    c = (char) ldat.lerProximoCaractere();
                    if(c == 'T') {
                        c = (char) ldat.lerProximoCaractere();
                        if(c == 'O') {
                            return new Token("ENQTO", TipoToken.PCEnqto); // ---- PCEnqto -----
                        }
                    }
                } else if( c == 'T') {
                    if(c == 'A') {
                        c = (char) ldat.lerProximoCaractere();
                        if(c == 'O') {
                            return new Token("ENTAO", TipoToken.PCEntao); // ---- PCEntao -----
                        }
                    }
                }
            } else {
                return new Token("E", TipoToken.OpBoolE); // ---- OpBoolE -----
            }
        }
        
        //FIM
        else if (c == 'F') {
            c = (char) ldat.lerProximoCaractere();
            if (c == 'I') {
                c = (char) ldat.lerProximoCaractere();
                if (c == 'M') return new Token("FIM", TipoToken.PCFim); // ---- PCFim -----
            }
        } 
        
        else if (c == 'O') {
            c = (char) ldat.lerProximoCaractere();
            if (c == 'U') return new Token("OU", TipoToken.OpBoolOu); // ---- OpBoolOu -----
        } 
        
        //operadores aritmericos + - * /
        else if (c == '+') return new Token("+", TipoToken.OpAritSoma); // ---- OpAritSoma -----
        else if (c == '-') return new Token("-", TipoToken.OpAritSub); // ---- OpAritSub -----
        else if (c == '*') return new Token("*", TipoToken.OpAritMult); // ---- OpAritMult -----
        else if (c == '/') return new Token("/", TipoToken.OpAritDiv); // ---- OpAritDiv -----

        
        // : e :=
        else if (c == ':') {
            c = (char) ldat.lerProximoCaractere();
            this.look_forward = c;
            if (c == '=') return new Token(":=", TipoToken.Atrib); // ---- Atrib -----
            else {
                return new Token(":", TipoToken.Delim); // ---- Delim -----
            }
        }

        //<= <
        else if (c == '<') {
            c = (char) ldat.lerProximoCaractere();
            this.look_forward = c;
            if (c == '=') return new Token("<=", TipoToken.OpRelMenorIgual); // ---- OpRelMenorIgual -----
            return new Token("<", TipoToken.OpRelMenor);  // ---- OpRelMenor -----
        }
        
        // > >=
        else if (c == '>') {
            c = (char) ldat.lerProximoCaractere();
            this.look_forward = c;
            if (c == '=') return new Token(">=", TipoToken.OpRelMaiorIgual); // ---- OpRelMaiorIgual -----
            return new Token(">", TipoToken.OpRelMaior);  // ---- OpRelMaior -----
        }
        
        //  ==
        else if (c == '=') {
            this.look_forward = c;
            c = (char) ldat.lerProximoCaractere();
            if (c == '=') return new Token("==", TipoToken.OpRelIgual); // ---- OpRelIgual -----
        }
        
        //  !=
        else if (c == '!') {
            c = (char) ldat.lerProximoCaractere();
            if (c == '=') {
                return new Token("!=", TipoToken.OpRelDif); // ---- OpRelDif -----
            }
        }

        //parenteses 
        else if (c == '(') return new Token("(", TipoToken.AbrePar); // ---- AbrePar -----
        else if (c == ')') return new Token(")", TipoToken.FechaPar); // ---- FechaPar -----

        //numeros
        else if (Character.isDigit(c)) {
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
