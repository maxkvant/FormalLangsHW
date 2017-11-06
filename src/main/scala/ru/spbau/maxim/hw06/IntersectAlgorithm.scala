package ru.spbau.maxim.hw06

import guru.nidi.graphviz.model.MutableGraph

object IntersectAlgorithm {
  def apply(grammar: Grammar, graph: MutableGraph): ChomskyNF = {
    solveChomskyNF(ChomskyNF.toNF(grammar), graph)
  }

  def solveChomskyNF(grammar: ChomskyNF, graph: MutableGraph): ChomskyNF = ???
}
