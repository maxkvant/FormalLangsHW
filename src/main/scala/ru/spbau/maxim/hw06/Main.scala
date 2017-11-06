package ru.spbau.maxim.hw06

import java.io.{FileInputStream, PrintWriter}

import guru.nidi.graphviz.model.MutableGraph
import guru.nidi.graphviz.parse.Parser
import ru.spbau.maxim.hw06.ChomskyNF.NonTerminalGen
import ru.spbau.maxim.hw06.Grammar.toLine

import scala.io.Source

object Main {
  import Conversions._
  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      println("Usage: sbt \"run grammarFile graphFile.dot outputFile\"")
    } else {
      val grammarFile = args(0)
      val graphDotFile = args(1)
      val outputFile = args(2)

      val grammarFileLines = Source.fromFile(grammarFile).getLines().toList
      val grammar = GrammarParser.parseRules(grammarFileLines)
      val graph: MutableGraph = Parser.read(new FileInputStream(graphDotFile))

      val resGrammar = IntersectAlgorithm(grammar, graph)

      val resLines = resGrammar.rules.map(toLine)
      val printWriter = new PrintWriter(outputFile)
      resLines.foreach(printWriter.println)
      printWriter.close()
    }
  }

  def example1(): Unit = {
    val rules: Seq[(NonTerminal, Seq[GrammarSymbol])]= Seq(
      ("S", Seq("S", "S")),
      ("S", Seq('[', "S", ']')),
      ("S", Seq(Eps))
    )

    val nonTerminalGen: NonTerminalGen = new ChomskyNF.NonTerminalGen
    val grammar = SimpleGrammar("S", rules)

    val nf = ChomskyNF.toNF(grammar)
    println(nf)

    val cyk = CYK(nf)
    println(cyk(""))
    println(cyk("[][[[]]]"))
    println(cyk("[][[]"))
  }

  def example2(): Unit = {
    val grammarFile = "grammar.txt"
    val graphDotFile = "automaton.dot"

    val grammarFileLines = Source.fromFile(grammarFile).getLines().toList
    val grammar = GrammarParser.parseRules(grammarFileLines)
    val graph: MutableGraph = Parser.read(new FileInputStream(graphDotFile))

    val resGrammar = IntersectAlgorithm(grammar, graph)
    val cyk = CYK(resGrammar)
    println(cyk("aacbb"))
  }
}
