package ru.spbau.maxim.hw08.grammar

object Conversions {
  implicit def toNonTerminal(s: String): NonTerminal = StringNonTerminal(s)
  implicit def toTerminal(c: Char): Terminal = CharTerminal(c)
}
