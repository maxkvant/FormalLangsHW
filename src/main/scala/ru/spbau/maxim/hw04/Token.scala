package ru.spbau.maxim.hw04

sealed abstract class Token(str: String, l: Int, r: Int)

case class Separator(str: String, l: Int, r: Int) extends Token(str, l, r)

case class Identifier(str: String, l: Int, r: Int) extends Token(str, l, r)

case class KeyWord(str: String, l: Int, r: Int) extends Token(str, l, r)

case class Operator(str: String, l: Int, r: Int) extends Token(str, l, r)

abstract class Literal(str: String, l: Int, r: Int) extends Token(str, l, r)

case class Bool(str: String, l: Int, r: Int) extends Literal(str, l, r)

case class FloatNumber(str: String, l: Int, r: Int) extends Literal(str, l, r)

case class IntNumber(str: String, l: Int, r: Int) extends Literal(str, l, r)

case class Comment(str: String, l: Int, r: Int) extends Token(str, l, r)
