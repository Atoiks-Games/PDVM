grammar Assembly;

fragment A: [aA];
fragment B: [bB];
fragment C: [cC];
fragment D: [dD];
fragment E: [eE];
fragment F: [fF];
fragment G: [gG];
fragment H: [hH];
fragment I: [iI];
fragment J: [jJ];
fragment K: [kK];
fragment L: [lL];
fragment M: [mM];
fragment N: [nN];
fragment O: [oO];
fragment P: [pP];
fragment Q: [qQ];
fragment R: [rR];
fragment S: [sS];
fragment T: [tT];
fragment U: [uU];
fragment V: [vV];
fragment W: [wW];
fragment X: [xX];
fragment Y: [yY];
fragment Z: [zZ];

LABEL:  ':' [a-zA-Z0-9_]*;
NUMBER: [0-9]+ | '$' [0-9A-Fa-f]+;

OP_HLT: H L T;
OP_ADD_A: A D D '_' A;
OP_ADD_P: A D D '_' P;
OP_SUB_A: S U B '_' A;
OP_SUB_P: S U B '_' P;
OP_MUL_A: M U L '_' A;
OP_MUL_P: M U L '_' P;
OP_DIV_A: D I V '_' A;
OP_DIV_P: D I V '_' P;
OP_MOD_A: M O D '_' A;
OP_MOD_P: M O D '_' P;
OP_A2C: A '2' C;
OP_P2C: P '2' C;
OP_CH2A: C H '2' A;
OP_CH2P: C H '2' P;
OP_CL2A: C L '2' A;
OP_CL2P: C L '2' P;
OP_AP2C: A P '2' C;
OP_SEND: S E N D;
OP_RECV: R E C V;
OP_LDA: L D A;
OP_LDP: L D P;
OP_JMP: J M P;
OP_JALP: J A L P;
OP_JAGP: J A G P;
OP_JAEP: J A E P;
OP_JANP: J A N P;
OP_STHA: S T H A;
OP_STFA: S T F A;
OP_LDHA: L D H A;
OP_LDFA: L D F A;
OP_SWAP: S W A P;
OP_PHC: P H C;
OP_PPC: P P C;
OP_RET: R E T;
OP_JSR: J S R;
OP_C2S: C '2' S;
OP_S2C: S '2' C;
OP_SWSC: S W S C;
OP_LDS: L D S;
OP_ADD: A D D;
OP_SUB: S U B;
OP_MUL: M U L;
OP_DIV: D I V;
OP_MOD: M O D;
OP_M2C: M '2' C;

COMMA: ',';
OPEN: '(';
CLOSE: ')';

REG_IP: '%' I P;
REG_SP: '%' S P;
REG_A: '%' A;
REG_P: '%' P;
REG_C: '%' C;

WS: [ \t\r\n]+ -> Channel(HIDDEN);
CM: ';' ~[\r\n]* -> Channel(HIDDEN);

register
    : REG_IP # regIP
    | REG_SP # regSP
    | REG_A # regA
    | REG_P # regP
    | REG_C # regC
    ;

program
    : opline*
    ;

opline
    : OP_HLT # opHlt
    | OP_ADD_A # opAddA
    | OP_ADD_P # opAddP
    | OP_SUB_A # opSubA
    | OP_SUB_P # opSubP
    | OP_MUL_A # opMulA
    | OP_MUL_P # opMulP
    | OP_DIV_A # opDivA
    | OP_DIV_P # opDivP
    | OP_MOD_A # opModA
    | OP_MOD_P # opModP
    | OP_A2C # opA2C
    | OP_P2C # opP2C
    | OP_CH2A # opCh2a
    | OP_CH2P # opCh2p
    | OP_CL2A # opCl2a
    | OP_CL2P # opCl2p
    | OP_AP2C # opAp2c
    | OP_SEND # opSend
    | OP_RECV # opRecv
    | OP_LDA immediate # opLda
    | OP_LDP immediate # opLdp
    | OP_JMP immediate # opJmp
    | OP_JALP immediate # opJalp
    | OP_JAGP immediate # opJagp
    | OP_JAEP immediate # opJaep
    | OP_JANP immediate # opJanp
    | OP_STHA memory # opStha
    | OP_STFA memory # opStfa
    | OP_LDHA memory # opLdha
    | OP_LDFA memory # opLdfa
    | OP_SWAP # opSwap
    | OP_PHC # opPhc
    | OP_PPC # opPpc
    | OP_RET # opRet
    | OP_JSR immediate # opJsr
    | OP_C2S # opC2S
    | OP_S2C # opS2C
    | OP_SWSC # opSwsc
    | OP_LDS immediate # opLds
    | OP_ADD register # opAdd
    | OP_SUB register # opSub
    | OP_MUL register # opMul
    | OP_DIV register # opDiv
    | OP_MOD register # opMod
    | OP_M2C register # opM2C
    | LABEL # defLabel
    ;

immediate
    : n=NUMBER
    | LABEL
    ;

memory
    : OPEN (n=NUMBER | indirectY) CLOSE
    | OPEN indirect CLOSE
    | OPEN indirectX CLOSE
    ;

indirect
    : addr=NUMBER COMMA register
    | register COMMA addr=NUMBER
    ;

indirectX
    : OPEN indirect CLOSE
    ;

indirectY
    : OPEN addr=NUMBER CLOSE COMMA register
    | register COMMA OPEN addr=NUMBER CLOSE
    ;