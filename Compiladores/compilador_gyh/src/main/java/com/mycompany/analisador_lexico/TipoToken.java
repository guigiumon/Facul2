package com.mycompany.analisador_lexico;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author a2614103
 */
public enum TipoToken {
PCDec, PCProg, PCInt, PCReal, PCLer, PCImprimir, PCSe, PCEntao, PCSenao, PCEnqto, PCIni, PCFim,
OpAritMult, OpAritDiv, OpAritSoma, OpAritSub,
OpRelMenor, OpRelMenorIgual, OpRelMaior, OpRelMaiorIgual, OpRelIgual, OpRelDif,
OpBoolE, OpBoolOu,
Delim,
Atrib,
AbrePar, FechaPar,
Var,
NumInt,
NumReal,
Cadeia;
}
