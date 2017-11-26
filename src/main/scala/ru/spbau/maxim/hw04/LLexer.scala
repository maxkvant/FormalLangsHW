package ru.spbau.maxim.hw04

import com.simplytyped._
import org.antlr.v4.runtime._
import org.antlr.v4.runtime.tree._

import scala.collection.mutable.ArrayBuffer

object LLexer {
  def parse(lStr: String): List[Token] = {
    val input = new ANTLRInputStream(lStr)
    val lexer = new LLexer(input)
    val lexerTokens = new CommonTokenStream(lexer)
    val parser = new LParser(lexerTokens)
    val tree = parser.l

    val tokens = new ArrayBuffer[Token]

    def addRule(ctx: ParserRuleContext): Unit = {
      val str: String = ctx.getText
      val l = ctx.start.getStartIndex
      val r = ctx.stop.getStopIndex
      val cur: Option[Token] = ctx match {
        case ctx1: LParser.SeparatorContext => Some(Separator(str, l, r))
        case ctx1: LParser.IdentifierContext => Some(Identifier(str, l, r))
        case ctx1: LParser.KeyWordContext => Some(KeyWord(str, l, r))
        case ctx1: LParser.OperatorContext => Some(Operator(str, l, r))
        case ctx1: LParser.FloatNumberContext => Some(FloatNumber(str, l, r))
        case ctx1: LParser.IntNumberContext => Some(IntNumber(str, l, r))
        case ctx1: LParser.CommentContext => Some(Comment(str.substring(2, str.length), l, r))
        case ctx1: LParser.BoolContext => Some(Bool(str, l, r))
        case _ => None
      }
      cur.foreach(token => tokens += token)
    }

    def visitTree(tree: Tree): Unit = {
      val n = tree.getChildCount
      for (i <- 0 until n) {
        visitTree(tree.getChild(i))
      }
      tree.getPayload match {
        case ctx: ParserRuleContext => addRule(ctx)
        case _ =>
      }
    }

    visitTree(tree)

    tokens.toList
  }

  def main(args: Array[String]): Unit = {
    require(args.length == 1)
    val filePath = args(0)
    val str = scala.io.Source.fromFile(filePath).mkString
    val res = parse(str)
    for (x <- res) {
      println(x)
    }
  }
}
