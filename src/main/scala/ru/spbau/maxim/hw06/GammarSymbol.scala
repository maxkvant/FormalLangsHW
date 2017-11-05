package ru.spbau.maxim.hw06


sealed trait GrammarSymbol {
  def terminal: Boolean
}

sealed class Terminal extends GrammarSymbol {
  override def terminal = true
}

case class Eps() extends Terminal

case class CharTerminal(char: Char) extends Terminal {
  override def toString: String = char.toString
}

class NonTerminal extends GrammarSymbol {
  override def terminal: Boolean = false
}

case class StringNonTerminal(name: String) extends NonTerminal {
  override def toString: String = name
}