package ru.spbau.maxim.hw08

import java.io.File

import guru.nidi.graphviz.engine.{Format, Graphviz}
import guru.nidi.graphviz.parse.Parser
import ru.spbau.maxim.hw08.grammar.{EndStr, GrammarSymbol, NonTerminal}

object Main {
  import ru.spbau.maxim.hw08.grammar.Conversions._

  def main(array: Array[String]): Unit = {
    val rules1: Seq[(NonTerminal, Seq[GrammarSymbol])] = Seq(
      ("E'", Seq("E", EndStr)),
      ("E", Seq("T")),
      ("E", Seq("E", '+', "T")),
      ("T", Seq('n')),
      ("T", Seq('[', "E", ']'))
    )
    println(rules1)
    val lr0 = LR0("E'", rules1)
    outputDot(lr0, "pngs/out.png")

    val rules2: Seq[(NonTerminal, Seq[GrammarSymbol])] = Seq(
      ("S'", Seq("S", EndStr)),
      ("S", Seq()),
      ("S", Seq("S", '(', "S", ')'))
    )

    val lR0_2 = LR0("S'", rules2)
    outputDot(lR0_2, "pngs/out2.png")
  }

  def outputDot(lR0: LR0, path: String): Unit = {
    val gr = Parser.read(lR0.toDotStr)
    val outfile = new File(path)
    lR0.print()
    println(Format.PNG)
    Graphviz.fromGraph(gr).render(Format.PNG).toFile(outfile)
  }
}
