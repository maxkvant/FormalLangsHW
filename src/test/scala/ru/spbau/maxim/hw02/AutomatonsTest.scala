package ru.spbau.maxim.hw02

import org.scalatest._
import Automatons._

class AutomatonsTest extends FlatSpec with Matchers {
  "wordsDFA" should
    "check this words" in {
    def accept1: String => Boolean = Automatons.accepts(wordsDFA)
    accept1("if") should be (true)
    accept1("then") should be (true)
    accept1("else") should be (true)
    accept1("let") should be (true)
    accept1("in") should be (true)
    accept1("true") should be (true)
    accept1("false") should be (true)
    accept1("o") should be (false)
    accept1("_") should be (false)
    accept1("9") should be (false)
    accept1("") should be (false)
    accept1("fals") should be (false)
    accept1("inn") should be (false)
  }

  "identifierDFA" should
    "check this words" in {
    def accept1: String => Boolean = Automatons.accepts(identifierDFA)
    accept1("a") should be (true)
    accept1("_a") should be (true)
    accept1("a_b") should be (true)
    accept1("ident") should be (true)
    accept1("_i_d_") should be (true)
    accept1("__agent_007") should be (true)
    accept1("_13") should be (true)
    accept1("e2_e4") should be (true)
    accept1("") should be (false)
    accept1("1") should be (false)
    accept1("!myVar") should be (false)
    accept1("iDeNt") should be (false)
    accept1("_I_D_") should be (false)
  }

  "resDFA" should
    "check this words" in {
    def accept1: String => Boolean = Automatons.accepts(resDFA)
    accept1("12_a") should be (false)
    accept1("_a12") should be (true)
    accept1("in") should be (false)
  }

}
