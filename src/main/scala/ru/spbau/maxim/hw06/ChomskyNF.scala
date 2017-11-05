package ru.spbau.maxim.hw06

import scala.collection.{immutable, mutable}

object ChomskyNF {
  import Grammar._
  
  class NonTerminalGen {
    private var num = 0
    def apply(): NonTerminal = {
      num += 1
      StringNonTerminal(s"N$num")
    }
  }

  def toNF(grammar: Grammar): Grammar = {
    implicit val nonTerminalGen: NonTerminalGen = new NonTerminalGen

    val grammar1 = ReduceLongRules(grammar)
    val (grammar2, needS1) = RemoveEpsRules(grammar1)
    val grammar3 = reduceTerminals(grammar2)
    val grammarCur = RemoveUnitRules(grammar3)

    if (needS1) {
      val newStart = StringNonTerminal("S'")
      SimpleGrammar(newStart, grammarCur.rules ++ Seq(newStart -> Seq(Eps()), newStart -> Seq(grammarCur.start)))
    } else {
      grammarCur
    }
  }

  def reduceTerminals(grammar: Grammar)(implicit nonTerminalGen: NonTerminalGen): Grammar = {
    val terminals = grammar.terminals()
    val terminalMap: Map[GrammarSymbol, NonTerminal] = terminals
      .map(symbol => symbol -> nonTerminalGen())
      .toMap

    val newRules: Seq[Rule] = grammar.rules.map({case (nonTerminal, word) =>
      val modifiedWord = word.map {
        case symbol: NonTerminal => symbol
        case symbol: Terminal => terminalMap(symbol)
      }
      (nonTerminal, modifiedWord)
    }) ++ terminalMap.map({case (terminal, nonTerminal) => (nonTerminal, Seq(terminal))})
    SimpleGrammar(grammar.start, newRules)
  }

  def ReduceLongRules(grammar: Grammar)(implicit nonTerminalGen: NonTerminalGen): Grammar = {
    def genRules(nonTerminal: NonTerminal, word: Word): List[Rule] = {
      if (word.size <= 2) {
        List((nonTerminal, word))
      } else {
        val nonTerminal2 = nonTerminalGen()
        (nonTerminal, Seq(word.head, nonTerminal2)) :: genRules(nonTerminal2, word.tail)
      }
    }
    val newRules = grammar.rules.flatMap({case (nonTerminal, word) => genRules(nonTerminal, word)})
    SimpleGrammar(grammar.start, newRules)
  }

  def RemoveEpsRules(grammar: Grammar): (Grammar, Boolean) = {
    val prev = grammar.rules.flatMap({case (nonTerminal, word) =>
      word.map(symbol => (symbol, word, nonTerminal))
    })
      .groupBy(_._1)
      .mapValues(_.map({case (symbol, word, nonTerminal) => (nonTerminal, word)}))

    val epsRules: Set[NonTerminal] = {
      val curEpsRules: mutable.Set[NonTerminal] = mutable.Set()
      def checkWord(word: Word): Boolean = word.forall {
        case symbol: Eps => true
        case symbol: NonTerminal => curEpsRules(symbol)
        case _ => false
      }
      def fillRules(nonTerminal: NonTerminal, word: Word) {
        if (!curEpsRules(nonTerminal) && checkWord(word)) {
          curEpsRules += nonTerminal
          for ((nonTerminal2, word) <- prev(nonTerminal)) {
            fillRules(nonTerminal2, word)
          }
        }
      }
      grammar.rules.foreach({case (nonTerminal, word) => fillRules(nonTerminal, word)})
      curEpsRules
    }.toSet

    val newRules: Seq[Rule] = grammar.rules.flatMap({case (nonTerminal, word) =>
      val word2 = word.filter(!_.isInstanceOf[Eps])
      def gen(word : List[GrammarSymbol]): List[List[GrammarSymbol]] = {
        word match {
          case Nil => List(Nil)
          case symbol :: word1 =>
            val genWithout = gen(word1)
            val genWith = genWithout.map(symbol :: _)
            symbol match {
              case symbol: NonTerminal => if (epsRules(symbol)) genWith ++ genWithout else genWith
              case _ => genWith
            }
        }
      }.distinct
      gen(word2.toList).filter(_.nonEmpty).map(_.toSeq).map(word => (nonTerminal, word))
    })
    (SimpleGrammar(grammar.start, newRules), epsRules(grammar.start))
  }

  def RemoveUnitRules(grammar: Grammar): Grammar = {
    val wordsGroups: Map[NonTerminal, Seq[Word]] = grammar.rules
      .groupBy({case (nonTerminal, _) => nonTerminal})
      .mapValues(_.map({case (_, word) => word}))

    def isUnit(word: Word): Boolean = {
      word match {
        case Seq(symbol) => symbol.isInstanceOf[NonTerminal]
        case _ => false
      }
    }

    val unitRules: Map[NonTerminal, Seq[NonTerminal]] = grammar.rules
      .filter({case (_, word) => isUnit(word)})
      .groupBy({case (nonTerminal, _) => nonTerminal})
      .mapValues(_.map(_._2.head.asInstanceOf[NonTerminal]))
      .withDefault(_ => Seq[NonTerminal]())

    def getByUnitRules(nonTerminal: NonTerminal): Set[NonTerminal] = {
      val byUnitRules: mutable.Set[NonTerminal] = mutable.Set()
      def dfs(nonTerminal: NonTerminal): Unit = {
        if (!byUnitRules(nonTerminal)) {
          byUnitRules += nonTerminal
          for (nonTerminal2 <- unitRules(nonTerminal)) {
            dfs(nonTerminal2)
          }
        }
      }
      dfs(nonTerminal)
      byUnitRules.toSet
    }

    val newRules: Seq[Rule] = wordsGroups.keys.flatMap({ nonTerminal =>
      val byUnitRules: Set[NonTerminal] = getByUnitRules(nonTerminal)
      for {
        nonTerminal2 <- byUnitRules.toSeq
        word <- wordsGroups(nonTerminal2)
        if !isUnit(word)
      } yield (nonTerminal, word)
    }).toSeq
    SimpleGrammar(grammar.start, newRules)
  }
  
  def isChomskyNF(grammar: Grammar): Boolean = {
    true
  }
}

class ChomskyNF
