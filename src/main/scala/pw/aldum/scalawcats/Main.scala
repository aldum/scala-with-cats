package pw.aldum
package scalawcats

import cats.syntax.either.*
import cats.data.Validated.{Valid, Invalid}

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

@main def Main(args: String*): Unit =
  import scalawcats.given

  def fPrintln[A](f: Future[A]): Unit =
    println(Await.result(f, 1.second))
  val x = 75

  val a: Check[List[String], Int, Int] =
    Check.Pure(
      Predicate.Pure[List[String], Int](v =>
        if v > 2 then Valid(v)
        else Invalid(List("Must be > 2"))
      )
    )

  val b: Check[List[String], Int, Int] =
    Check.Pure(
      Predicate.Pure[List[String], Int](v =>
        if v % 2 == 0 then Valid(v)
        else Invalid(List("Must be even"))
      )
    )

  val check: Check[List[String], Int, Int] =
    a andThen b

  println("─" * x)
  println(check(5))
  println(check(4))
  println(check(1))
  println("─" * x)
