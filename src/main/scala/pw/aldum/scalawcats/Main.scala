package pw.aldum
package scalawcats

import cats.syntax.flatMap.*
import cats.syntax.all.toFunctorOps

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  println("─" * x)
  println("─" * x)
