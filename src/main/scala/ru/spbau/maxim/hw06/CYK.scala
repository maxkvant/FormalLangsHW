package ru.spbau.maxim.hw06

class CYK(grammar: Grammar) {
  private val rulesSingleGroups: Map[Terminal, Set[NonTerminal]] = grammar.rules
    .filter({case (nonTerminal, word) => word.size == 1 && nonTerminal != grammar.start})
    .map({case (nonTerminal, word) => (nonTerminal, word.head.asInstanceOf[Terminal]) })
    .groupBy(_._2)
    .mapValues(_.map(_._1).toSet)
    .withDefault(_ => Set())

  private val rulesBinary: Seq[(NonTerminal, (NonTerminal, NonTerminal))] = grammar.rules
    .filter({case (nonTerminal, word) => word.size == 2 })
    .map({case (nonTerminal, word) =>
      (nonTerminal, (word(0).asInstanceOf[NonTerminal], word(1).asInstanceOf[NonTerminal]))
    })

  private val rulesBinaryGroups: Map[(NonTerminal, NonTerminal), Set[NonTerminal]] = rulesBinary
    .groupBy(_._2)
    .mapValues(_.map(_._1).toSet)
    .withDefault(_ => Set())

  private val wordsStart: Set[Seq[GrammarSymbol]] = grammar.rules
    .filter({case (nonTerminal, word) => nonTerminal == grammar.start})
    .map(_._2).toSet

  def checkWord(s: String, grammar: Grammar): Boolean = {
    if (s.isEmpty) {
      grammar.rules.contains((grammar.start, Seq(Eps())))
    } else {
      val n = s.length
      val dp = Array.fill[Set[NonTerminal]](n+1, n+1)(Set())

      for (l <- 0 until n) {
        dp(1)(l) = rulesSingleGroups(CharTerminal(s(l)))
      }

      for (len <- 2 to n;
           l1 <- 0 to n - len)
            {
        dp(len)(l1) =  (Set[NonTerminal]() /: (for {
          len1 <- 1 until len
          len2 = len - len1
          l2 = l1 + len1
          n1 <- dp(len1)(l1)
          n2 <- dp(len2)(l2)
        } yield rulesBinaryGroups((n1, n2))))(_ ++ _)
      }

      dp(n)(0).exists(nonTerminal => wordsStart.contains(Seq(nonTerminal)))
    }
  }
}
