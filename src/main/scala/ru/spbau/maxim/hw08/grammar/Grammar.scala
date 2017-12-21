package ru.spbau.maxim.hw08.grammar

trait Grammar {
  import Grammar._

  def start: NonTerminal
  def rules: Seq[Rule]

  def terminals(): Set[Terminal] = {
    rules.flatMap({case (_, word) => word})
      .filter(_.isInstanceOf[Terminal])
      .map(_.asInstanceOf[Terminal])
      .toSet
  }
}

object Grammar {
  type Word = Seq[GrammarSymbol]
  type Rule = (NonTerminal, Word)

  def toLine(rule: Rule): String = rule match {
    case (nonTerminal, word) => s"$nonTerminal: ${word.mkString(" ")}"
  }
}
