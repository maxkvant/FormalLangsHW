package ru.spbau.maxim.hw04

import com.simplytyped._
import org.antlr.v4.runtime._

object LLexer {
  def jsonExample(): Unit = {
    val jsonStr: String = """
                            |{
                            |  "student":
                            |  {
                            |    "id" : "12345678",
                            |    "prename" : "John",
                            |    "surname" : "Doe",
                            |    "address" :
                            |	   {
                            |	     "street" : "Johndoestreet",
                            |	     "postcode" : "99999"
                            |	   },
                            |    "email"   : "johndoe@doe.com"
                            |  }
                            |}
                          """.stripMargin
    println(jsonStr)
    val input = new ANTLRInputStream(jsonStr)
    val lexer = new JSONLexer(input)
    val tokens = new CommonTokenStream(lexer)
    val parser = new JSONParser(tokens)
    val tree = parser.json
    val treeStr = tree.toStringTree(parser)
    println(treeStr)
  }

  def lExample(): Unit = {
    val lStr = "read xx xx ; // asdf[] \n if y + 1 == xx then write y else write xx\n"
    print(lStr)
    val input = new ANTLRInputStream(lStr)
    val lexer = new LLexer(input)
    val tokens = new CommonTokenStream(lexer)
    val parser = new LParser(tokens)
    val tree = parser.l
    val treeStr = tree.toStringTree(parser)
    println(treeStr)
  }

  def main(args: Array[String]): Unit = {
    //jsonExample()
    lExample()
  }


}
