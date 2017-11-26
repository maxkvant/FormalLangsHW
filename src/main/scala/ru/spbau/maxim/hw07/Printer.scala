package ru.spbau.maxim.hw07

import scala.collection.mutable.ArrayBuffer

object Printer {
  def toStr(program: Program): String = {
    var count = 0
    var tabs = 0
    val output = ArrayBuffer[String]()

    def getCount(): Int = {
      val res = count
      count += 1
      res
    }

    def dfs(token: Token, id: Int): Unit = {
      tabs += 1
      token.toTreeList.foreach {
        case token2: Token =>
          val u = getCount()
          addVertex(u, token2.vertexName)
          addEdge(id, u)
          dfs(token2, u)
        case x: AnyRef =>
          val u = getCount()
          addVertex(u, x.toString)
          addEdge(id, u)
      }
      tabs -= 1
    }

    def addVertex(v: Int, s: String): Unit = {
      addStr(v.toString + " [label=\"" + s + "\"]")
    }

    def addStr(s: String): Unit = {
      output += "   " * tabs + s
    }

    def addEdge(v: Int, u: Int): Unit = {
      addStr(s"$v -> $u")
    }

    val v = getCount()
    addVertex(v, program.vertexName)
    dfs(program, v)

    val body = output.mkString("\n") + "\n"
    "digraph program {\n" +
      body +
      "}\n"
  }

}
