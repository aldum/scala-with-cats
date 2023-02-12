package pw.aldum
package scalawcats

import cats.instances.int.*
import cats.Foldable

import scala.language.postfixOps

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  println("─" * x)

  val l = List(1, 2, 3)

  println(Foldable[List].foldLeft(l, 0)(_ + _))
  println(Foldable[List].foldMap(l)(_.toString))

  val ints = List(Vector(1, 2, 3), Vector(4, 5, 6))
  println( (Foldable[List] `compose` Foldable[Vector]).combineAll(ints) )

  println("─" * x)
