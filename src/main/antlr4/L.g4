grammar L;

l: (comment | keyWord | literal | separator | operator| identifier)* EOF;

comment: LineComment ;
LineComment:  '//' ~[\n\r]* ;

identifier: Identifier ;
Identifier: [_a-zA-Z] [_a-zA-Z0-9]* ;

keyWord: 'if'
       | 'then'
       | 'else'
       | 'while'
       | 'do'
       | 'read'
       | 'write'
       | 'begin'
       | 'end'
       ;

literal: bool
       | floatNumber
       | intNumber
       ;

intNumber: Digits;

bool: 'true' | 'false' ;

separator: Separator;
Separator: [();] ;

operator: '+'
        | '-'
        | '/'
        | '*'
        | '%'
        | '=='
        | '!='
        | '>'
        | '>='
        | '<'
        | '<='
        | '&&'
        | '||'
        | ':='
        ;

WS  :  [ \t\r\n\f]+ -> skip
    ;

floatNumber: FloatingPointLiteral;

//@see https://github.com/antlr/grammars-v4/blob/master/java/Java.g4

Digits
    :   [0-9] ([0-9_]* [0-9])?
    ;

FloatingPointLiteral
    :   DecimalFloatingPointLiteral
    |   HexadecimalFloatingPointLiteral
    ;

fragment
DecimalFloatingPointLiteral
    :   Digits '.' Digits? ExponentPart? FloatTypeSuffix?
    |   '.' Digits ExponentPart? FloatTypeSuffix?
    |   Digits ExponentPart FloatTypeSuffix?
    |   Digits FloatTypeSuffix
    ;

fragment
ExponentPart
    :   ExponentIndicator SignedInteger
    ;

fragment
ExponentIndicator
    :   [eE]
    ;

fragment
SignedInteger
    :   Sign? Digits
    ;

fragment
Sign
    :   [+-]
    ;

fragment
FloatTypeSuffix
    :   [fFdD]
    ;

fragment
HexadecimalFloatingPointLiteral
    :   HexSignificand BinaryExponent FloatTypeSuffix?
    ;

fragment
HexSignificand
    :   HexNumeral '.'?
    |   '0' [xX] HexDigits? '.' HexDigits
    ;

fragment
BinaryExponent
    :   BinaryExponentIndicator SignedInteger
    ;

fragment
BinaryExponentIndicator
    :   [pP]
    ;

fragment
HexNumeral
    :   '0' [xX] HexDigits
    ;

fragment
HexDigits
    :   HexDigit (HexDigitOrUnderscore* HexDigit)?
    ;

fragment
HexDigit
    :   [0-9a-fA-F]
    ;

fragment
HexDigitOrUnderscore
    :   HexDigit
    |   '_'
    ;