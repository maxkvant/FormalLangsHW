package ru.spbau.maxim.hw06

import guru.nidi.graphviz.model.Label

sealed trait GrammarSymbol

sealed trait Terminal extends GrammarSymbol {
  def char: Char
}

trait NonTerminal extends GrammarSymbol

case object Eps extends GrammarSymbol {
  override def toString: String = "eps"
}

case class CharTerminal(char: Char) extends Terminal {
  override def toString: String = char.toString
}

case class StringNonTerminal(name: String) extends NonTerminal {
  override def toString: String = name
}

case class LabeledNonTerminal(label1: Label, nonTerminal: NonTerminal, label2: Label) extends NonTerminal {
  override def toString: String = s"($label1,$nonTerminal,$label2)"
}

case class LabeledTerminal(label1: Label, char: Char, label2: Label) extends Terminal {
  override def toString: String = s"($label1,$char,$label2)"
}