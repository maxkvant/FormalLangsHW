package ru.spbau.maxim.hw06

import java.io.{FileInputStream, PrintWriter}

import guru.nidi.graphviz.model.MutableGraph
import guru.nidi.graphviz.parse.Parser
import ru.spbau.maxim.hw06.Grammar.toLine

import scala.io.Source

object Main {
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
}
