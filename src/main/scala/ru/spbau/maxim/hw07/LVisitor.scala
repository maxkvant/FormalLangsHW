package ru.spbau.maxim.hw07

import com.simplytyped._
import ru.spbau.maxim.hw07.Token.Statements

import scala.collection.JavaConverters._


class LVisitor extends LBaseVisitor[Token] {
  override def visitExpr(ctx: LParser.ExprContext): Expr = {
    if (ctx.identifier() != null) {
      ExprVariable(visitIdentifier(ctx.identifier()))
    } else if (ctx.intNumber() != null) {
      IntLiteral(ctx.getText.toInt, ctx.start.getStartIndex, ctx.stop.getStopIndex)
    } else if (ctx.binaryOperation() != null) {
      visitBinaryOperation(ctx.binaryOperation())
    } else {
      visitExpr(ctx.expr())
    }
  }

  override def visitBinaryOperation(ctx: LParser.BinaryOperationContext): BinOp = {
    val args = ctx.expr().asScala.toList
    val l = ctx.start.getStartIndex
    val r = ctx.stop.getStopIndex
    BinOp(visitExpr(args.head), ctx.operator().getText, visitExpr(args(1)), l, r)
  }

  override def visitAssignment(ctx: LParser.AssignmentContext): Assignment = {
    val l = ctx.start.getStartIndex
    val r = ctx.stop.getStopIndex
    Assignment(visitIdentifier(ctx.identifier()),
      visitExpr(ctx.expr()),
      l, r)
  }

  override def visitRead(ctx: LParser.ReadContext): Read = {
    val l = ctx.start.getStartIndex
    val r = ctx.stop.getStopIndex
    Read(visitIdentifier(ctx.identifier()), l, r)
  }

  override def visitWrite(ctx: LParser.WriteContext): Write = {
    val l = ctx.start.getStartIndex
    val r = ctx.stop.getStopIndex
    Write(visitExpr(ctx.expr()), l, r)
  }

  override def visitIfNoElse(ctx: LParser.IfNoElseContext): IfNoElse = {
    val l = ctx.start.getStartIndex
    val r = ctx.stop.getStopIndex
    IfNoElse(visitExpr(ctx.expr()),
      visitBlockWithBraces2(ctx.blockWithBraces()),
      l, r)
  }

  override def visitIfElse(ctx: LParser.IfElseContext): IfElse = {
    val l = ctx.start.getStartIndex
    val r = ctx.stop.getStopIndex
    val blocks = ctx.blockWithBraces().asScala
    IfElse(visitExpr(ctx.expr()),
      visitBlockWithBraces2(blocks.head),
      visitBlockWithBraces2(blocks(1)),
      l, r)
  }

  override def visitWhileStatement(ctx: LParser.WhileStatementContext): While = {
    val l = ctx.start.getStartIndex
    val r = ctx.stop.getStopIndex
    While(visitExpr(ctx.expr()),
      visitBlockWithBraces2(ctx.blockWithBraces()),
      l, r)
  }

  def visitEmptyBlock2(ctx: LParser.EmptyBlockContext): Statements = List()

  def visitBlockWithBraces2(ctx: LParser.BlockWithBracesContext): Statements = {
    visitBlocks2(ctx.blocks())
  }

  def visitBlocks2(ctx: LParser.BlocksContext): Statements = {
    ctx.block().asScala.map(visitBlock).toList
  }

  override def visitBlock(ctx: LParser.BlockContext): Statement = {
    if (ctx.assignment() != null) {
      visitAssignment(ctx.assignment())
    } else if (ctx.read() != null) {
      visitRead(ctx.read())
    } else if (ctx.write() != null) {
      visitWrite(ctx.write())
    } else if (ctx.whileStatement() != null) {
      visitWhileStatement(ctx.whileStatement())
    } else if (ctx.ifNoElse() != null) {
      visitIfNoElse(ctx.ifNoElse())
    } else if (ctx.ifElse() != null) {
      visitIfElse(ctx.ifElse())
    } else if (ctx.functionCall() != null) {
      visitFunctionCall(ctx.functionCall())
    } else {
      throw new RuntimeException()
    }
  }

  override def visitFunctionCall(ctx: LParser.FunctionCallContext): FunctionCall = {
    val l = ctx.start.getStartIndex
    val r = ctx.stop.getStopIndex
    val params: List[Identifier] = ctx.params().identifier().asScala.map(visitIdentifier).toList
    val name = visitIdentifier(ctx.identifier())
    FunctionCall(name, params, l, r)
  }

  override def visitIdentifier(ctx: LParser.IdentifierContext): Identifier = {
    Identifier(ctx.getText,
      ctx.start.getStartIndex, ctx.stop.getStopIndex)
  }

  override def visitFunction(ctx: LParser.FunctionContext): Function = {
    val l = ctx.start.getStartIndex
    val r = ctx.stop.getStopIndex
    val params: List[Identifier] = ctx.params().identifier().asScala.map(visitIdentifier).toList
    val name = visitIdentifier(ctx.identifier())
    val statements = visitBlockWithBraces2(ctx.blockWithBraces())
    Function(name, params, statements, l, r)
  }

  override def visitProgram(ctx: LParser.ProgramContext): Program = {
    val l = ctx.start.getStartIndex
    val r = ctx.stop.getStopIndex
    val functions = ctx.function().asScala.map(visitFunction).toList
    val body = visitBlockWithBraces2(ctx.blockWithBraces())
    Program(functions, body, l, r)
  }
}
