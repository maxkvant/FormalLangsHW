package ru.spbau.maxim.hw06

import ru.spbau.maxim.hw06.Grammar.Rule

trait Grammar {
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


