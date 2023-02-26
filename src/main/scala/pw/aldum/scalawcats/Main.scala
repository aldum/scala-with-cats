package pw.aldum
package scalawcats

import cats.syntax.either.*

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

@main def Main(args: String*): Unit =
  import scalawcats.given

  def fPrintln[A](f: Future[A]): Unit =
    println(Await.result(f, 1.second))
  val x = 75

  val a: CheckF[List[String], Int] =
    CheckF { v =>
      if (v > 2) v.asRight
      else List("Must be > 2").asLeft
    }

  val b: CheckF[List[String], Int] =
    CheckF { v =>
      if (v < -2) v.asRight
      else List("Must be < -2").asLeft
    }

  val check: CheckF[List[String], Int] =
    a and b

  println("─" * x)
  println(check(5))
  println(check(0))
  println("─" * x)
