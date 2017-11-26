package ru.spbau.maxim.hw07

object Token {
  type Statements = List[Statement]
  type Params = List[Identifier]
  type Functions = List[Function]
}

import ru.spbau.maxim.hw07.Token._

class Identifier(val name: String, l: Int, r: Int) extends Token(l, r)

//Expressions

sealed abstract class Token(val l: Int, val r: Int)

sealed abstract class Expr(l: Int, r: Int) extends Token(l, r)

case class BinOp(left: Expr,
                 op: String,
                 right: Expr,
                 override val l: Int, override val r: Int) extends Expr(l, r)

case class ExprVariable(identifier: Identifier) extends Expr(identifier.l, identifier.r)

case class IntLiteral(value: Int,
                      override val l: Int, override val r: Int) extends Expr(l, r)

//Statements

sealed abstract class Statement(l: Int, r: Int) extends Token(l, r)

class Assignment(val identifier: Identifier, val expr: Expr, l: Int, r: Int) extends Statement(l, r)

case class While(condition: Expr,
                 statements: Statements,
                 override val l: Int, override val r: Int) extends Statement(l, r)

case class Read(identifier: Identifier,
                override val l: Int, override val r: Int) extends Statement(l, r)

case class IfNoElse(condition: Expr,
                    statements: Statements,
                    override val l: Int, override val r: Int) extends Statement(l, r)

case class IfElse(condition: Expr,
                  statementsIf: Statements,
                  statementsElse: Statements,
                  override val l: Int, override val r: Int) extends Statement(l, r)

//Function

case class Function(name: Identifier,
                    params: Params,
                    statements: Statements,
                    override val l: Int, override val r: Int) extends Statement(l, r)

case class FunctionCall(name: Identifier, params: Params,
                        override val l: Int, override val r: Int) extends Statement(l, r)

//Program
case class Program(functions: Functions,
                   body: Statements,
                   override val l: Int, override val r: Int) extends Token(l, r)