package pw.aldum
package scalawcats

import cats.instances.int.*

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  println("─" * x)

  val l = List(1, 2, 3)
  println(l.foldMap(_ + 2))
  println(l.foldFlatMap((x) => List(x * 2)))
  println(l.foldFilter(_ % 2 == 0))
  println(l.foldSum)
  println(l.monoidSum)

  println("─" * x)
