package ru.spbau.maxim.hw03

import org.scalatest.{FlatSpec, FunSuite, Matchers}

class Automatons3Test extends FlatSpec with Matchers {
  import Automatons3._

  "arrDFA" should
    "check this arrays" in {
    def accept1: String => Boolean = accepts(arrDFA)
    accept1("[]") should be (true)
    accept1("[1]") should be (true)
    accept1("[ -012 ]") should be (true)
    accept1("[1;2]") should be (true)
    accept1("[1;1;2;3;5;8]") should be (true)
    accept1("[ ]") should be (true)
    accept1(" [ 4; 8; 15; 16    ; 23; 42] ") should be (true)

    accept1("[;1;2;3;5;8]") should be (false)
    accept1("[1;1;2;3;5;]") should be (false)
    accept1("][") should be (false)
    accept1("[[]]") should be (false)
    accept1("[1; 2; 3") should be (false)
    accept1("[a]") should be (false)
    accept1("[1, 2, 3]") should be (false)
    accept1("[1; 2;]") should be (false)
    accept1("[1; 23 4; 5]") should be (false)
  }

  "tupleDFA" should
    "check this tuples" in {
    def accept1: String => Boolean = accepts(tupleDFA)
    accept1("()") should be (true)
    accept1("(1)") should be (true)
    accept1("(1,1,2,3,5,8)") should be (true)
    accept1("(    )") should be (true)

    accept1("([])") should be (true)
    accept1("([1; 2; 3])") should be (true)
    accept1("(eight)") should be (true)
    accept1("(eight, [1; 2 ; 3], 1)") should be (true)

    accept1(")(") should be (false)
    accept1("(())") should be (false)
    accept1("(1,2,3") should be (false)
    accept1("(1;2;3") should be (false)
    accept1("(1,2,)") should be (false)
    accept1("(1, 23 4, 5)") should be (false)
  }
}
