grammar L;

program: function* blockWithBraces
       ;

function: 'def' identifier '(' params ')' blockWithBraces;

callParams: (expr (',' expr)*)?
      ;

params: (identifier (',' identifier)*)?
      ;


block: assignment
     | write
     | read
     | whileStatement
     | ifNoElse
     | ifElse
     | functionCall
     ;

functionCall: identifier '(' callParams ')' ';'
            ;

assignment: identifier ':=' expr ';'
          ;


blocks: block*
      ;

blockWithBraces: '{' blocks '}'
               ;

nonEmptyBlockWithBraces: '{' block blocks '}'
                       ;

emptyBlock: '{' '}'
          ;

write: 'write' '(' expr ')' ';'
     ;

read: 'read' '(' identifier ')' ';'
    ;

whileStatement: 'while' expr blockWithBraces;

ifElse: 'if' expr blockWithBraces 'else' nonEmptyBlockWithBraces
      ;

ifNoElse: 'if' expr blockWithBraces 'else' emptyBlock
      ;

identifier: Identifier;

expr: intNumber
    | identifier
    | binaryOperation
    | '(' expr ')'
    ;

binaryOperation: '(' expr operator expr ')'
               ;

intNumber: Digits;

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

Identifier: [_a-zA-Z] [_a-zA-Z0-9]* ;

Digits
    :   [0-9] ([0-9]*)
    ;

WS  :  [ \t\r\n\f]+ -> skip
    ;
