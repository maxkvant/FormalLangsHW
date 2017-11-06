package ru.spbau.maxim.hw06

import guru.nidi.graphviz.parse.Parser
import org.scalatest.{FlatSpec, Matchers}
import ru.spbau.maxim.hw06.Conversions._

class CYKTest extends FlatSpec with Matchers {
  val rules: Seq[(NonTerminal, Seq[GrammarSymbol])] = Seq(
    ("S", Seq("S", "S")),
    ("S", Seq('[', "S", ']')),
    ("S", Seq(Eps))
  )

  val grammar = SimpleGrammar("S", rules)

  "CYK" should "check this words" in {
    val nf: ChomskyNF = ChomskyNF(grammar)

    val cyk = CYK(nf)

    cyk("") should be(true)
    cyk("[][[[]]]") should be(true)
    cyk("[][[]") should be(false)
    cyk("[[]]]") should be(false)
    cyk("[]") should be(true)
  }

  it should "check this intersection" in {
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

    cyk("[][[[]]]") should be(false)
    cyk("[[[[]]]]") should be(false)
    cyk("[[]][]") should be(true)
    cyk("[[]]") should be(true)
    cyk("") should be(false)
    cyk("[[]][][]") should be(true)
  }

  it should "check this intersection with epsilon" in {
    val graph = Parser.read(
      """| digraph {
         |   3 [shape = "doublecircle"]
         |   0 [shape = "doublecircle"]
         |   0 -> 1 [label = "["]
         |   1 -> 2 [label = "["]
         |   2 -> 3 [label = "]"]
         |   3 -> 3 [label = "["]
         |   3 -> 3 [label = "]"]
         | }
      """.stripMargin)

    val grammar2 = IntersectAlgorithm(grammar, graph)
    val cyk = CYK(grammar2)

    cyk("[][[[]]]") should be(false)
    cyk("[[[[]]]]") should be(false)
    cyk("[[]][]") should be(true)
    cyk("[[]]") should be(true)
    cyk("") should be(true)
    cyk("[[]][][]") should be(true)
  }
}
