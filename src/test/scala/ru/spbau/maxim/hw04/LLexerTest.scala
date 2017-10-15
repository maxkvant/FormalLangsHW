package ru.spbau.maxim.hw04

import org.scalatest.{FlatSpec, Matchers}

class LLexerTest extends FlatSpec with Matchers {

  import LLexer.parse

  "LLexer " should
    "parse this KeyWords" in {
    parse("if") should be(KeyWord("if", 0, 1) :: Nil)
    parse("then") should be(KeyWord("then", 0, 3) :: Nil)
    parse("else") should be(KeyWord("else", 0, 3) :: Nil)
    parse("while") should be(KeyWord("while", 0, 4) :: Nil)
    parse(" do\n") should be(KeyWord("do", 1, 2) :: Nil)
    parse("read") should be(KeyWord("read", 0, 3) :: Nil)
    parse("write") should be(KeyWord("write", 0, 4) :: Nil)
    parse("begin") should be(KeyWord("begin", 0, 4) :: Nil)
    parse("end") should be(KeyWord("end", 0, 2) :: Nil)
  }

  "LLexer " should
    "parse this operators" in {
    parse("+") should be(Operator("+", 0, 0) :: Nil)
    parse("-") should be(Operator("-", 0, 0) :: Nil)
    parse("*") should be(Operator("*", 0, 0) :: Nil)
    parse("/") should be(Operator("/", 0, 0) :: Nil)
    parse("%") should be(Operator("%", 0, 0) :: Nil)
    parse("<") should be(Operator("<", 0, 0) :: Nil)
    parse(">") should be(Operator(">", 0, 0) :: Nil)

    parse("==") should be(Operator("==", 0, 1) :: Nil)
    parse("!=") should be(Operator("!=", 0, 1) :: Nil)
    parse(">=") should be(Operator(">=", 0, 1) :: Nil)
    parse("<=") should be(Operator("<=", 0, 1) :: Nil)
    parse("&&") should be(Operator("&&", 0, 1) :: Nil)
    parse("||") should be(Operator("||", 0, 1) :: Nil)
  }

  "LLexer " should
    "parse separators" in {
    parse("(") should be(Separator("(", 0, 0) :: Nil)
    parse(")") should be(Separator(")", 0, 0) :: Nil)
    parse(",") should be(Separator(",", 0, 0) :: Nil)
    parse(";") should be(Separator(";", 0, 0) :: Nil)
  }

  "LLexer " should
    "parse literals" in {
    parse("true") should be(Bool("true", 0, 3) :: Nil)
    parse("false") should be(Bool("false", 0, 4) :: Nil)
    parse("1.0") should be(FloatNumber("1.0", 0, 2) :: Nil)
    parse("12.3f") should be(FloatNumber("12.3f", 0, 4) :: Nil)
    parse("0.3e-5") should be(FloatNumber("0.3e-5", 0, 5) :: Nil)
  }
}
