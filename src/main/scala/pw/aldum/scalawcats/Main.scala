package pw.aldum
package scalawcats

import cats.syntax.flatMap.*
import cats.syntax.all.toFunctorOps

import scala.concurrent.ExecutionContext.Implicits.global

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  println("─" * x)
  val f1 = getPowerLevel("asdf").value
  val f2 = getPowerLevel("Jazz").value
  println(f1.value)
  println(f2.value)

  println("─" * x)
