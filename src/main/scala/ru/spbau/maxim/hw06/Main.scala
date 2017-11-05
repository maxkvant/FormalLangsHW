package ru.spbau.maxim.hw06

import ru.spbau.maxim.hw06.ChomskyNF.NonTerminalGen

import uk.co.turingatemyhamster.graphvizs._
import uk.co.turingatemyhamster.graphvizs.dsl._
import uk.co.turingatemyhamster.graphvizs.exec._

object Main {
  import Conversions._
  def main(args: Array[String]): Unit = {
    val grammarFile = args(0)
    val grammarDotFile = args(1)
  }

  def example1(): Unit = {
    val rules: Seq[(NonTerminal, Seq[GrammarSymbol])]= Seq(
      ("S", Seq("S", "S")),
      ("S", Seq('[', "S", ']')),
      ("S", Seq(Eps()))
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