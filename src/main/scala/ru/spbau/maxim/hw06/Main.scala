package ru.spbau.maxim.hw06

import java.io.FileInputStream

import guru.nidi.graphviz.model.MutableGraph
import guru.nidi.graphviz.parse.Parser
import ru.spbau.maxim.hw06.ChomskyNF.NonTerminalGen
import ru.spbau.maxim.hw06.Grammar.toLine

import scala.io.Source

object Main {
  import Conversions._
  def main(args: Array[String]): Unit = {
    val grammarFile = "grammar.txt"
    val grammarFileLines = Source.fromFile(grammarFile).getLines().toList
    val graphDotFile = "automaton.dot"

    val grammar = GrammarParser.parseRules(grammarFileLines)
    val graph: MutableGraph = Parser.read(new FileInputStream(graphDotFile))

    println(grammar)
    grammar.rules.map(toLine).foreach(println)
    println(graph)

    example1()
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

    val cyk = new CYK(nf)
    println(cyk(""))
    println(cyk("[][[[]]]"))
    println(cyk("[][[]"))
  }
}
