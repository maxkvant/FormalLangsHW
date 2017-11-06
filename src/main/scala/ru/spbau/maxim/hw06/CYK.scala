package ru.spbau.maxim.hw06

case class CYK(grammar: ChomskyNF) {
  private val rulesSingleGroups: Map[Char, Set[NonTerminal]] = grammar.terminalRules
    .groupBy(_._2.char)
    .mapValues(_.map(_._1).toSet)
    .withDefault(_ => Set())

  private val rulesBinaryGroups: Map[(NonTerminal, NonTerminal), Set[NonTerminal]] = grammar.nonTerminalRules
    .groupBy(_._2)
    .mapValues(_.map(_._1).toSet)
    .withDefault(_ => Set())

  def apply(s: String): Boolean = {
    if (s.isEmpty) {
      grammar.startSymbols.contains(Eps)
    } else {
      val n = s.length
      val dp = Array.fill[Set[NonTerminal]](n+1, n+1)(Set())

      for (l <- 0 until n) {
        dp(1)(l) = rulesSingleGroups(s(l))
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

      dp(n)(0).exists(grammar.startSymbols.contains(_))
    }
  }
}
