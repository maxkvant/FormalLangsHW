package ru.spbau.maxim.hw02

import de.dominicscheurer.fsautils._
import de.dominicscheurer.fsautils.RegularExpressions._
import de.dominicscheurer.fsautils.Conversions._

object Primitives extends FSA_DSL {
  def toSymbols(chars: List[Char]): List[Symbol] = chars.map(_.toString).map(c => Symbol(c))
  def toSymbols(str: String): List[Symbol] = toSymbols(str.toList)

  def concat(reList: List[RE]): RE = (reList.head /: reList.tail) ((re1: RE, re2) => re1 & re2)

  def or(reList: List[RE]): RE = (reList.head /: reList.tail) ((re1: RE, re2) => re1 + re2)

  def toWordRE(str: String): RE = concat(toSymbols(str).map(c => c: RE))


  def charSet(symbols: List[Any]): RE = symbols match {
    case symbols: List[Symbol] => or(symbols.map(c => c: RE))
    case chars: List[Char] => or(toSymbols(chars).map(c => c: RE))
  }
}

object Automatons extends FSA_DSL {
  import Primitives._

  def main(args: Array[String]): Unit = {
    List(wordsDFA, identifierDFA, resDFA).foreach(println)
  }

  private val words: List[String] = List("if", "then", "else", "let", "in", "true", "false")

  private val letterFromWord: Set[Symbol] = Set[Symbol]() ++ words.flatMap(toSymbols)
  private val otherLetter: Set[Symbol] = (Set[Symbol]() ++ ('a' to 'z').map(x => Symbol(x.toString))) -- letterFromWord
  private val digit: Set[Symbol] = Set[Symbol]() ++ ('0' to '9').map(x => Symbol(x.toString))
  private val alph: Set[Symbol] =  letterFromWord + '_ + 'otherLetter + 'digit

  private def simplify(nfa: NFA): DFA = nfa
    .extendAlphabet(alph).removeUnreachable.toDFA.minimize.getRenamedCopy(1)

  val wordsDFA: DFA = simplify(or(words.map(toWordRE)).toNFA)

  val identifierDFA: DFA = simplify(simplify(charSet((alph - 'digit).toList).toNFA) ++
                                    simplify((charSet(alph.toList) *).toNFA))

  val resDFA: DFA = simplify(identifierDFA \ wordsDFA)

  def accepts(dfa: DFA)(s: String): Boolean = {
    def toAlph(str: String): List[Symbol] = {
      val chars = str.toList
      chars.map(
        char => {
          val symbol = Symbol(char.toString)
          if (digit.contains(symbol))
            'digit
          else if (otherLetter.contains(symbol))
            'otherLetter
          else
            symbol
        })
    }
    dfa.accepts(toAlph(s))
  }
}