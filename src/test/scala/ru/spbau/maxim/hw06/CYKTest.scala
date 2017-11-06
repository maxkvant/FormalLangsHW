package ru.spbau.maxim.hw06

import guru.nidi.graphviz.parse.Parser
import org.scalatest.{FlatSpec, Matchers}
import ru.spbau.maxim.hw06.Conversions._

class CYKTest extends FlatSpec with Matchers {
  "CYK" should "check this words" in {
    val rules: Seq[(NonTerminal, Seq[GrammarSymbol])] = Seq(
      ("S", Seq("S", "S")),
      ("S", Seq('[', "S", ']')),
      ("S", Seq(Eps))
    )

    val nf: ChomskyNF = ChomskyNF(SimpleGrammar("S", rules))

    val cyk = CYK(nf)

    cyk("") should be(true)
    cyk("[][[[]]]") should be(true)
    cyk("[][[]") should be(false)
    cyk("[[]]]") should be(false)
    cyk("[]") should be(true)
  }

  it should "check this intersection" in {
    val rules: Seq[(NonTerminal, Seq[GrammarSymbol])] = Seq(
      ("S", Seq("S", "S")),
      ("S", Seq('[', "S", ']')),
      ("S", Seq(Eps))
    )
    val grammar = SimpleGrammar("S", rules)
    val graph = Parser.read(
      """| digraph {
         |   3 [shape = "doublecircle"]
         |   0 -> 1 [label = "["]
         |   1 -> 2 [label = "["]
         |   2 -> 3 [label = "]"]
         |   3 -> 3 [label = "["]
         |   3 -> 3 [label = "]"]
         | }
      """.stripMargin)

    val grammar2 = IntersectAlgorithm(grammar, graph)
    val cyk = CYK(grammar2)

    grammar2.rules.map(Grammar.toLine).foreach(println)

    cyk("[][[[]]]") should be(false)
    cyk("[[[[]]]]") should be(false)
    cyk("[[]][]") should be(true)
    cyk("[[]]") should be(true)
    cyk("") should be(false)
    cyk("[[]][][]") should be(true)

  }
}
