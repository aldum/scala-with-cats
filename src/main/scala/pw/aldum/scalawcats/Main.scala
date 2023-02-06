package pw.aldum
package scalawcats

import cats.syntax.flatMap.*
import cats.syntax.all.toFunctorOps

import scala.concurrent.ExecutionContext.Implicits.global

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  println("─" * x)

  val f1 = tacticalReport("Ironhide", "Jazz")
  val f2 = tacticalReport("Jazz", "Bumblebee")
  val f3 = tacticalReport("Hot Rod", "Bumblebee")

  println(f1)
  println(f2)
  println(f3)

  println("─" * x)
