package ru.spbau.maxim.hw06

import ru.spbau.maxim.hw06.Grammar.{Rule, Word}

import scala.util.parsing.combinator._

object GrammarParser extends JavaTokenParsers {
  override def skipWhitespace: Boolean = true

  def parseRules(strings: Seq[String]): Grammar = {
    val rules: Seq[Rule] = strings.map(str =>
      parseAll(rule, str) match {
        case Success(matched: Rule, _) => matched
        case Failure(msg, _) => throw new RuntimeException("parsing FAILURE: " + msg)
        case Error(msg, _) => throw new RuntimeException("parsing ERROR: " + msg)
      }
    )
    SimpleGrammar(rules.head._1, rules)
  }

  private def rule: Parser[Rule] = (nonTerminal ~ ":" ~ word) ^^ {
    case nonTerminal ~ _ ~ word => (nonTerminal, word)
  }

  private def word: Parser[Word] = rep(eps | terminal | nonTerminal)

  private def terminal: Parser[CharTerminal] = """[a-z]""".r ^^ { str => CharTerminal(str.head) }

  private def eps: Parser[GrammarSymbol] = "eps" ^^ { _ => Eps }

  private def nonTerminal: Parser[NonTerminal] = """[A-Z]""".r ^^ { str => StringNonTerminal(str) }
}
