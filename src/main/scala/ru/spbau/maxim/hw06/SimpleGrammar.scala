package ru.spbau.maxim.hw06

import ru.spbau.maxim.hw06.Grammar.Rule

case class SimpleGrammar(start: NonTerminal, rules: Seq[Rule]) extends Grammar
