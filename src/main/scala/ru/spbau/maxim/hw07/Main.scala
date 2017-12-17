package ru.spbau.maxim.hw07

import java.io.File

import com.simplytyped.{LLexer, LParser}
import guru.nidi.graphviz.engine.{Format, Graphviz}
import guru.nidi.graphviz.parse.Parser
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}

object Main {
  def main(args: Array[String]): Unit = {
    require(args.length == 1)
    val filePath = args(0)
    val str = scala.io.Source.fromFile(filePath).mkString
    val tree = parseProgram(str)
    val res = Printer.toStr(parseProgram(str))

    import java.io._
    val pw = new PrintWriter(new File("output.dot"))
    pw.write(res)
    pw.close()

    outputPng(res)
  }

  def outputPng(dotStr: String): Unit = {
    val gr = Parser.read(dotStr)
    val outfile = new File("pngs/out.png")
    println(Format.PNG)
    Graphviz.fromGraph(gr).render(Format.PNG).toFile(outfile)
  }

  def parseProgram(lStr: String): Program = {
    val input = new ANTLRInputStream(lStr)
    val lexer = new LLexer(input)
    val lexerTokens = new CommonTokenStream(lexer)
    val parser = new LParser(lexerTokens)
    new LVisitor().visit(parser.program()).asInstanceOf[Program]
  }
}
