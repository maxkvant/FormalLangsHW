package ru.spbau.maxim.hw08

import ru.spbau.maxim.hw08.grammar.{EndStr, GrammarSymbol, NonTerminal}

object Main {
  import ru.spbau.maxim.hw08.grammar.Conversions._

  def main(array: Array[String]): Unit = {
    val rules: Seq[(NonTerminal, Seq[GrammarSymbol])] = Seq(
      ("E'", Seq("E", EndStr)),
      ("E", Seq("T")),
      ("E", Seq("E", '+', "T")),
      ("T", Seq('n')),
      ("T", Seq('[', "E", ']'))
    )
    println(rules)
    LR0("E'", rules).print()
  }
}
