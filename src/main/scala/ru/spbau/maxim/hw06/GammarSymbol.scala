package ru.spbau.maxim.hw06

import guru.nidi.graphviz.model.Label

sealed trait GrammarSymbol {
  def isTerminal: Boolean
}

sealed trait Terminal extends GrammarSymbol {
  override def isTerminal = true
}

case object Eps extends Terminal {
  override def toString: String = "eps"
}

case class CharTerminal(char: Char) extends Terminal {
  override def toString: String = char.toString
}

trait NonTerminal extends GrammarSymbol {
  override def isTerminal: Boolean = false
}

case class StringNonTerminal(name: String) extends NonTerminal {
  override def toString: String = name
}

case class LabeledNonTerminal(label1: Label, nonTerminal: NonTerminal, label2: Label) extends NonTerminal {
  override def toString: String = s"($label1,$nonTerminal,$label2)"
}

case class LabeledTerminal(label1: Label, terminal: Terminal, label2: Label) extends Terminal {
  override def toString: String = s"($label1,$terminal,$label2)"
}