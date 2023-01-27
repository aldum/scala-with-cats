package pw.aldum
package scalawcats

import cats.Functor
import cats.instances.list.*   // for Functor
import cats.instances.option.* // for Functor
import cats.instances.function.* // for Functor
import cats.syntax.functor.*     // for map

@main def Main(args: String*): Unit =
  import Tree.*
  import scalawcats.given
  val x = 75

  val t = Branch(
      Branch(Leaf(2)
          , Branch(Leaf(3), Leaf(4)) )
      , Leaf(5)
    )

  println("─" * x)
  println(t.map(_ * 2))
  println("─" * x)
