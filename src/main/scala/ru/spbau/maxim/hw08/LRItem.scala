package ru.spbau.maxim.hw08

import ru.spbau.maxim.hw08.grammar.Grammar._
import ru.spbau.maxim.hw08.grammar.GrammarSymbol

case class LRItem(base: GrammarSymbol, pref: Word, suf: Word) {
  override def toString: String = base + ":- " + pref.mkString("") + "." + suf.mkString("")
}

case class Edge(v: Int, symbol: GrammarSymbol, u: Int)