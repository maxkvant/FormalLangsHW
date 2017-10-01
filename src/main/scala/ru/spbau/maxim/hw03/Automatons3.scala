package ru.spbau.maxim.hw03


import de.dominicscheurer.fsautils._
import de.dominicscheurer.fsautils.RegularExpressions._
import de.dominicscheurer.fsautils.Conversions._
import de.dominicscheurer.fsautils.gui.GraphvizBridge
import de.dominicscheurer.fsautils.FSA_DSL
import de.dominicscheurer.fsautils.RegularExpressions.RE
import ru.spbau.maxim.hw02.Automatons.{digit, otherLetter}
import ru.spbau.maxim.hw03.Automatons3.spaces

object Automatons3 extends FSA_DSL {
  import ru.spbau.maxim.hw02.Primitives._

  val letters: Set[Symbol] = ('a' to 'z').map(c => Symbol(c.toString)).toSet
  val digits: Set[Symbol] = ('0' to '9').map(c => Symbol(c.toString)).toSet

  val digit: Symbol = 'digit
  val space: Symbol = 'space
  val letter: Symbol = 'letter

  val identifier: RE = ('_ + letter) & (('_ + letter + digit)*)
  val spaces: RE = space*
  val number: RE =  spaces & (('- & digit) + digit) & (digit*) & spaces
  val arr: RE = spaces & Symbol("[") & (spaces + (number & ((Symbol(";") & number)*))) & Symbol("]") & spaces
  val tuple_el: RE = arr + number + (spaces & identifier & spaces)
  val tuple: RE = spaces & Symbol("(") & (spaces + (tuple_el & ((Symbol(",") & tuple_el)*))) & Symbol(")") & spaces

  private def simplify(nfa: NFA): DFA = nfa.removeUnreachable.toDFA.minimize.getRenamedCopy(1)

  lazy val tupleDFA: DFA = simplify(tuple.toNFA)
  lazy val arrDFA: DFA = simplify(arr.toNFA)

  def main(args: Array[String]): Unit = {
    writeToFile("arrDFA.dot") { _.write(GraphvizBridge.toDot(arrDFA)) }
    writeToFile("arrDFA.txt") { _.write(arrDFA.toString) }

    writeToFile("tupleDFA.dot") { _.write(GraphvizBridge.toDot(tupleDFA)) }
    writeToFile("tupleDFA.txt") { _.write(tupleDFA.toString) }
  }

  def accepts(dfa: DFA)(s: String): Boolean = {
    def toAlph(str: String): List[Symbol] = {
      val chars = str.toList
      chars.map(
        char => {
          val symbol = Symbol(char.toString)
          if (digits.contains(symbol))
            digit
          else if (letters.contains(symbol))
            letter
          else if (char == ' ')
            space
          else
            symbol
        })
    }
    dfa.accepts(toAlph(s))
  }
}
