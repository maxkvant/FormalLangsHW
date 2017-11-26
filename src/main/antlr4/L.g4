grammar L;

program: function* blockWithBraces
       ;

function: 'def' identifier '(' params ')' blockWithBraces;

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

functionCall: identifier '(' params ')'
            ;

blocks: block (';' block)* (';')?
      ;

blockWithBraces: '{' blocks '}'
                 ;

emptyBlock: '{' '}'
          ;

assignment: identifier ':=' expr
          ;

write: 'write' '(' expr ')'
     ;

read: 'read' '(' identifier ')'
    ;

whileStatement: 'while' expr blockWithBraces;

ifElse: 'if' expr 'then' blockWithBraces 'else' blockWithBraces
      ;

ifNoElse: 'if' expr 'then' blockWithBraces 'else' emptyBlock
      ;

identifier: Identifier;

expr: intNumber
    | identifier
    | binaryOperation
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
