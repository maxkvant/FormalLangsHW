package ru.spbau.maxim.hw06

import guru.nidi.graphviz.model.{MutableNodePoint, _}
import ru.spbau.maxim.hw06.ChomskyNF.{NonTerminalRule, TerminalRule}

import scala.collection.JavaConverters._
import scala.collection.mutable

object IntersectAlgorithm {
  def apply(grammar: Grammar, graph: MutableGraph): ChomskyNF = {
    solveChomskyNF(ChomskyNF(grammar), graph)
  }

  def solveChomskyNF(grammar: ChomskyNF, graph: MutableGraph): ChomskyNF = {
    val nodes: Iterable[MutableNode] = graph.nodes().asScala
    val automatonTerminals = nodes.filter(_.attrs().asScala.toSeq
      .exists({ mapEntry => mapEntry.getKey == "shape" && mapEntry.getValue == "doublecircle" })
    )

    val edges: Seq[Edge] = nodes.flatMap(getEdges)(collection.breakOut)
    val reacheble: Map[MutableNode, Set[MutableNode]] = nodes.map(node => (node, getReacheble(node))).toMap

    val singleRules = grammar.terminalRules.groupBy(_._2.char).mapValues(_.map(_._1))
    val newSingleRules: Seq[TerminalRule] = edges.flatMap(edge =>
      for {
        nonTerminal <- singleRules(edge.char)
      } yield (newN(edge.from, nonTerminal, edge.to),
        LabeledTerminal(edge.from.label(), edge.char, edge.to.label()))
    )

    val newNonTerminalRules: Seq[NonTerminalRule] = (for {
      i <- nodes
      j <- reacheble(i)
      k <- reacheble(j)
      (c, (a, b)) <- grammar.nonTerminalRules
      res = (newN(i, c, k), (newN(i, a, j), newN(j, b, k)))
    } yield res).toSeq

    val startNonTerminals = grammar.startSymbols
      .filter(_.isInstanceOf[NonTerminal])
      .map(_.asInstanceOf[NonTerminal])

    val minNode = nodes.minBy(_.label().toString)

    val needEps = automatonTerminals.exists(_ == minNode) && grammar.startSymbols.contains(Eps)

    val newStartSymbols1: Seq[GrammarSymbol] = (for {
      j <- automatonTerminals
      s <- startNonTerminals
    } yield newN(minNode, s, j)).toSeq

    val newStartSymbols: Seq[GrammarSymbol] = if (needEps) newStartSymbols1 ++ Seq(Eps) else newStartSymbols1

    ChomskyNF(newStartSymbols.distinct, newNonTerminalRules.distinct, newSingleRules.distinct)
  }

  private def getReacheble(node: MutableNode): Set[MutableNode] = {
    val readable: mutable.Set[MutableNode] = mutable.Set()

    def dfs(node: MutableNode): Unit = {
      if (!readable(node)) {
        readable += node
        for (edge: Edge <- node.links().asScala.map(toEdge)) {
          dfs(edge.to)
        }
      }
    }
    dfs(node)
    readable.toSet
  }

  private def getEdges(node: MutableNode): Seq[Edge] = {
    node.links().asScala.map(toEdge).toSeq
  }

  private def toEdge(link: Link): Edge = {
    val to: MutableNode = link.to().asInstanceOf[MutableNodePoint].node()
    val from: MutableNode = link.from().asInstanceOf[MutableNodePoint].node()

    val char = link.attrs().asScala.filter(_.getKey == "label").head.getValue match {
      case s: String => require(s.length == 1); s.head
      case _ => throw new RuntimeException("no label")
    }
    Edge(from, to, char)
  }

  private def newN(v: MutableNode, nonTerminal: NonTerminal, u: MutableNode): LabeledNonTerminal = {
    LabeledNonTerminal(v.label(), nonTerminal, u.label())
  }

  case class Edge(from: MutableNode, to: MutableNode, char: Char)
}
