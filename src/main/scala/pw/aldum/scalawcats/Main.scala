package pw.aldum
package scalawcats

import cats.syntax.flatMap.*
import cats.syntax.all.toFunctorOps

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  println("─" * x)

  val f1 = canSpecialMove("asdf", "Jazz")
  val f2 = canSpecialMove("Jazz", "Bumblebee")
  val f3 = canSpecialMove("Hot Rod", "Bumblebee")

  println(f1.value.value)
  println(f2.value.value)
  println(f3.value.value)

  println("─" * x)
