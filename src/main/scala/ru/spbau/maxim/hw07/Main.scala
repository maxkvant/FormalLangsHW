package ru.spbau.maxim.hw07

import com.simplytyped.{LLexer, LParser}
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}

object Main {
  def main(args: Array[String]): Unit = {
    require(args.length == 1)
    val filePath = "file.txt"
    //args(0)
    val str = scala.io.Source.fromFile(filePath).mkString
    val tree = parse(str)
    val res = Printer.toStr(parse(str))
    println(tree)
    println(res)

    import java.io._
    val pw = new PrintWriter(new File("hello.txt"))
    pw.write(res)
    pw.close()
  }

  def parse(lStr: String): Program = {
    val input = new ANTLRInputStream(lStr)
    val lexer = new LLexer(input)
    val lexerTokens = new CommonTokenStream(lexer)
    val parser = new LParser(lexerTokens)
    new LVisitor().visit(parser.program()).asInstanceOf[Program]
  }
}
