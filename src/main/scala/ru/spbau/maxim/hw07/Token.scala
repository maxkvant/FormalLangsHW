package ru.spbau.maxim.hw07

object Token {
  type Statements = List[Statement]
  type Params = List[Identifier]
  type Functions = List[Function]
}

import ru.spbau.maxim.hw07.Token._


sealed abstract class Token(val l: Int, val r: Int) {
  def toTreeList: List[AnyRef]

  def vertexName: String = s"$getName($l,$r)"

  def getName: String = this.getClass.getSimpleName
}

case class Identifier(name: String,
                      override val l: Int, override val r: Int) extends Token(l, r) {
  override def toTreeList: List[AnyRef] = List(this.name)
}

case class Program(functions: Functions,
                   body: Statements,
                   override val l: Int, override val r: Int) extends Token(l, r) {
  override def toTreeList: List[AnyRef] = functions ++ body
}

//Expressions

sealed abstract class Expr(l: Int, r: Int) extends Token(l, r)

case class BinOp(left: Expr,
                 op: String,
                 right: Expr,
                 override val l: Int, override val r: Int) extends Expr(l, r) {
  override def toTreeList = List(left, right)

  override def getName: String = op + " "
}

case class ExprVariable(identifier: Identifier) extends Expr(identifier.l, identifier.r) {
  override def toTreeList: List[AnyRef] = identifier.toTreeList
}

case class IntLiteral(value: Int,
                      override val l: Int, override val r: Int) extends Expr(l, r) {
  override def toTreeList: List[AnyRef] = List(value.toString)
}

//Statements

sealed abstract class Statement(l: Int, r: Int) extends Token(l, r)

case class Assignment(identifier: Identifier,
                      expr: Expr,
                      override val l: Int, override val r: Int) extends Statement(l, r) {
  override def toTreeList: List[AnyRef] = List(identifier, expr)

  override def getName: String = ":= "
}

case class While(condition: Expr,
                 statements: Statements,
                 override val l: Int, override val r: Int) extends Statement(l, r) {
  override def toTreeList: List[AnyRef] = List("while", "(", condition, ")") ++ statements
}

case class Read(identifier: Identifier,
                override val l: Int, override val r: Int) extends Statement(l, r) {
  override def toTreeList: List[AnyRef] = List(identifier.name)
}

case class Write(expr: Expr,
                 override val l: Int, override val r: Int) extends Statement(l, r) {
  override def toTreeList: List[AnyRef] = List(expr)
}


case class IfNoElse(condition: Expr,
                    statements: Statements,
                    override val l: Int, override val r: Int) extends Statement(l, r) {
  override def toTreeList: List[AnyRef] = List("if", condition, "then") ++ statements
}

case class IfElse(condition: Expr,
                  statementsIf: Statements,
                  statementsElse: Statements,
                  override val l: Int, override val r: Int) extends Statement(l, r) {
  override def toTreeList: List[AnyRef] =
    List("if", condition, "then") ++
      statementsIf ++
      List("else") ++
      statementsElse
}

//Functions

case class Function(name: Identifier,
                    params: Params,
                    statements: Statements,
                    override val l: Int, override val r: Int) extends Statement(l, r) {
  override def toTreeList: List[AnyRef] = List(name, "(") ++ params ++ List(")") ++ statements
}

case class FunctionCall(name: Identifier, params: Params,
                        override val l: Int, override val r: Int) extends Statement(l, r) {
  override def toTreeList: List[AnyRef] = List(name, "(") ++ params ++ List(")")
}