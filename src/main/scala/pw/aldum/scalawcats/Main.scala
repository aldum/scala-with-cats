package pw.aldum
package scalawcats

import cats.data.Writer

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.*
import cats.syntax.applicative.* // for pure
import cats.syntax.writer.*


given ExecutionContext = ExecutionContext.global

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  Await.result(Future.sequence(Vector(
    Future(factorial(5)),
    Future(factorial(5))
  )), 5.seconds)

  println("─" * x)
  println(factorial(5))
  println(factorial(6))
  println("─" * x)
