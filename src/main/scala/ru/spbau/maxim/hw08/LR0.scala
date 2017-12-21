package ru.spbau.maxim.hw08

import ru.spbau.maxim.hw08.grammar.Grammar.Rule
import ru.spbau.maxim.hw08.grammar.{GrammarSymbol, NonTerminal}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


case class LR0(start: NonTerminal, rules: Seq[Rule]) {
  val (vertexes: Seq[Set[LRItem]], edges: Seq[Edge]) = {
    val items0: Set[LRItem] = clojure(clojureAddition(start))
    val vertexes = mutable.Set[Set[LRItem]]()
    val ids = mutable.Map[Set[LRItem], Int]()
    val edges = mutable.Set[Edge]()
    val vertexes2 = ArrayBuffer[Set[LRItem]]()
    var count = 0

    def getId(curItems: Set[LRItem]): Int = {
      if (!vertexes.contains(curItems)) {
        vertexes += curItems
        vertexes2 += curItems
        ids += curItems -> count
        count += 1
      }
      ids(curItems)
    }

    def addEdges(v: Set[LRItem]): Unit = {
      if (!vertexes.contains(v)) {
        next(v).foreach {
          case (grammarSymbol, u) =>
            edges += Edge(getId(v), grammarSymbol, getId(u))
            addEdges(u)
          case _ =>
        }
      }
    }

    addEdges(items0)

    (vertexes2.toIndexedSeq, edges.toIndexedSeq)
  }
  private val clojureAddition: Map[GrammarSymbol, Seq[LRItem]] = rules
    .groupBy {case (n, _) => n.asInstanceOf[GrammarSymbol] }
    .mapValues { _.map { case (nonTerminal, word) => LRItem(nonTerminal, List(), word) } }
    .withDefault(_ => Seq())

  def print(): Unit = {
    vertexes.zipWithIndex.foreach {case (v, i) => println(s"$i: $v")}
    edges.foreach { println }
  }

  private def next(items: Set[LRItem]): Map[GrammarSymbol, Set[LRItem]] = items
        .filter(item => item.suf.nonEmpty)
        .groupBy { item => item.suf.head }
        .mapValues(_.map { case LRItem(nonTerminal, pref, suf) =>
          LRItem(nonTerminal, pref :+ suf.head, suf.tail)
        })
        .mapValues(items => clojure(items.toSeq))

  private def clojure(items: Seq[LRItem]): Set[LRItem] = {
    def getHeads(items: Seq[LRItem]) = items.flatMap { item => item.suf.headOption.toSet }

    val res: mutable.Set[GrammarSymbol] = mutable.Set() ++ getHeads(items)

    var prevSize = -1
    while (res.size != prevSize) {
      prevSize = res.size
      val addition: mutable.Set[GrammarSymbol] = res.flatMap { item => getHeads(clojureAddition(item)) }
      res ++= addition
    }
    (items ++ res.flatMap(clojureAddition)).toSet
  }
}
