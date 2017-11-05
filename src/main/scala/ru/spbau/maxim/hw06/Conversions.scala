package ru.spbau.maxim.hw06

object Conversions {
  implicit def toNonTerminal(s: String): NonTerminal = StringNonTerminal(s)
  implicit def toTerminal(c: Char): Terminal = CharTerminal(c)
}
