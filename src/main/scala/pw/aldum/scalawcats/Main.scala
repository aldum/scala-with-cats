package pw.aldum
package scalawcats

import cats.syntax.flatMap.*
import cats.syntax.all.toFunctorOps

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  import Tree.*

  val incrLeaf = (i: Int) => Leaf(i + 1)
  val dupLeaf = (i: Int) => Leaf(i * 2)
  val tsLeaf = (i: Int) => Leaf(s""""$i"""")

  println("─" * x)
  println(leaf(4))
  println(leaf(4).flatMap(incrLeaf))
  val t = Branch(
      Branch(Leaf(2)
          , Branch(Leaf(3), Leaf(4)) )
      , Leaf(5)
    )
  println(t)
  println(t.flatMap(dupLeaf))
  println(t.flatMap(tsLeaf))
  val ttt = for
    n  <- t
  yield n + 10
  println(ttt)

  println("─" * x)
