grammar L;



l: (keyWord | literal | comment | separator | operator| identifier)*? endLine ;


endLine: '\n'
       | '\r\n'
       | '\r'
       ;

comment:  COMMENT_START NOT_ENDL* endLine;
COMMENT_START: '//';
NOT_ENDL: ~('\n' | '\r') ;
ENDL_SYMBOL: [\n\r];

identifier: IDENTIFIER ;
IDENTIFIER:  INDENT_START NOT_WS* ;
fragment INDENT_START: ('_' | [a-zA-Z]) ;

WS  :   [ \t\f]+ -> skip ;
fragment NOT_WS: ~('\t' | '\n' | '\r' | '\f' | ' ') ;

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

literal: 'true'
       | 'false'
       | INT
       ;

separator: '(' | ')' | ',' | ';' ;

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
        ;

INT: '-'? N0 ;
fragment N0 : '0' | [1-9] [0-9]* ;