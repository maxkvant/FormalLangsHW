grammar L;

programm: function* blockWithBraces
       ;

function: 'def' identifier '(' params ')' blockWithBraces;

params: (identifier (',' identifier)*)?
      ;

identifier: Identifier;

expr: intNumber
    | identifier
    | '(' expr operator expr ')'
    ;

block: assignment
     | write
     | read
     | whileStatement
     | blockWithBraces
     | ifNoElse
     | ifElse
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

whileStatement: 'while' '(' expr ')' blockWithBraces;

ifElse: 'if' '(' expr ')' 'then' blockWithBraces 'else' blockWithBraces
      ;

ifNoElse: 'if' '(' expr ')' 'then' blockWithBraces 'else' emptyBlock
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

Identifier: [_a-zA-Z] ~[ \t\n\r]* ;

Digits
    :   [0-9] ([0-9]*)
    ;

WS  :  [ \t\r\n\f]+ -> skip
    ;
