package ru.spbau.maxim.hw08.grammar

sealed trait GrammarSymbol

sealed trait Terminal extends GrammarSymbol {
  def char: Char
}

trait NonTerminal extends GrammarSymbol

case object EndStr extends Terminal {
  override def char: Char = '$'

  override def toString: String = "$"
}

case class CharTerminal(char: Char) extends Terminal {
  override def toString: String = char.toString
}

case class StringNonTerminal(name: String) extends NonTerminal {
  override def toString: String = name
}