package ru.spbau.maxim.hw07

import org.scalatest.{FlatSpec, Matchers}

class ParserTest extends FlatSpec with Matchers {

  import Main.parseProgram

  "Parser" should "parse this assignments" in {
    implicit def toAssignment(program: Program): Assignment = program.body.statements.head.asInstanceOf[Assignment]

    val assignment1: Assignment = parseProgram("{ x := 2; }")

    (assignment1 match {
      case (Assignment(Identifier("x", _, _), IntLiteral(2, _, _), _, _)) => true
    }) should be(true)

    val assignment2: Assignment = parseProgram("{ x1 := (1 + a); }")

    (assignment2 match {
      case (Assignment(Identifier("x1", _, _),
      BinOp(IntLiteral(1, _, _), "+", ExprVariable(Identifier("a", _, _)), _, _),
      _, _)) => true
    }) should be(true)

    val assignment3: Assignment = parseProgram("{ x2 := (((0))); }")

    (assignment3 match {
      case (Assignment(Identifier("x2", _, _), IntLiteral(0, _, _), _, _)) => true
    }) should be(true)
  }

  "Parser" should "parse this read" in {
    implicit def toRead(program: Program): Read = program.body.statements.head.asInstanceOf[Read]

    val read1: Read = parseProgram("{ read(abacaba); }")

    (read1 match {
      case Read(Identifier("abacaba", _, _), _, _) => true
    }) should be(true)
  }

  "Parser" should "parse this write" in {
    implicit def toWrite(program: Program): Write = program.body.statements.head.asInstanceOf[Write]

    val write1: Write = parseProgram("{ write eee; }")

    (write1 match {
      case Write(ExprVariable(Identifier("eee", _, _)), _, _) => true
    }) should be(true)

    val write2: Write = parseProgram("{ write (x + 1); }")

    (write2 match {
      case Write(BinOp(ExprVariable(Identifier("x", _, _)),
      "+",
      IntLiteral(1, _, _),
      _, _),
      _, _) => true
    }) should be(true)
  }

  "Parser" should "parse this while" in {
    implicit def toWhile(program: Program): While = program.body.statements.head.asInstanceOf[While]

    val while1: While = parseProgram("{ while c { c := 0; } }")

    (while1 match {
      case While(ExprVariable(Identifier("c", _, _)),
      Block(List(Assignment(Identifier("c", _, _), IntLiteral(0, _, _), _, _)), _, _),
      _, _) => true
    }) should be(true)
  }

  "Parser" should "parse this ifNoElse" in {
    implicit def toIf(program: Program): IfNoElse = program.body.statements.head.asInstanceOf[IfNoElse]

    val if1: IfNoElse = parseProgram("{ if c { c := 0; } else { } }")

    (if1 match {
      case IfNoElse(ExprVariable(Identifier("c", _, _)),
      Block(List(Assignment(Identifier("c", _, _), IntLiteral(0, _, _), _, _)), _, _),
      _, _) => true
    }) should be(true)

    val if2: IfNoElse = parseProgram("{ if c { } else { } }")

    (if2 match {
      case IfNoElse(ExprVariable(Identifier("c", _, _)), Block(List(), _, _), _, _) => true
    }) should be(true)

  }

  "Parser" should "parse this ifElse" in {
    implicit def toIf(program: Program): IfElse = program.body.statements.head.asInstanceOf[IfElse]

    val if1: IfElse = parseProgram("{ if c { c := 0; } else { read(c); read(a); } }")

    (if1 match {
      case IfElse(ExprVariable(Identifier("c", _, _)),
      Block(List(Assignment(Identifier("c", _, _), IntLiteral(0, _, _), _, _)), _, _),
      Block(List(Read(Identifier("c", _, _), _, _), Read(Identifier("a", _, _), _, _)), _, _),
      _, _) => true
    }) should be(true)
  }

  "Parser" should "parse this functionCall" in {
    implicit def oFunctionCall(program: Program): FunctionCall = program.body.statements.head.asInstanceOf[FunctionCall]

    val f1: FunctionCall = parseProgram("{ f(); }")

    (f1 match {
      case FunctionCall(Identifier("f", _, _), List(), _, _) => true
    }) should be(true)

    val f2: FunctionCall = parseProgram("{ gcd(1, (n * 2)); }")

    (f2 match {
      case FunctionCall(Identifier("gcd", _, _),
      List(IntLiteral(1, _, _),
      BinOp(ExprVariable(Identifier("n", _, _)),
      "*",
      IntLiteral(2, _, _),
      _, _)),
      _, _) => true
    }) should be(true)
  }

  "Parser" should "parse this functionDef" in {
    implicit def toIf(program: Program): Function = program.functions.functions.head

    val f1: Function = parseProgram("def f() { read(x); } { }")

    (f1 match {
      case Function(Identifier("f", _, _),
      Params(List(), _, _),
      Block(List(Read(Identifier("x", _, _), _, _)), _, _)
      , _, _) => true
    }) should be(true)

    val f2: Function = parseProgram("def g(a, b, c) { x := ((a - b) - c); } { }")

    (f2 match {
      case Function(Identifier("g", _, _),
      Params(List(Identifier("a", _, _), Identifier("b", _, _), Identifier("c", _, _)), _, _),
      _, _, _) => true
    }) should be(true)

  }
}
