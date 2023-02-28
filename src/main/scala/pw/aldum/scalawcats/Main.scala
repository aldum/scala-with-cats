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

  def lenCheck(l: Int): Check[List[String], String, String] =
    Check.Pure(
      Predicate.Pure(v =>
        if v.size >= l then Valid(v)
        else Invalid(List(s"Must be at least $l long"))
      )
    )
  val alnum: Check[List[String], String, String] =
    Check.Pure(
      Predicate.Pure(v =>
        if v.forall(_.isLetterOrDigit) then Valid(v)
        else Invalid(List("Must be alphanumeric"))
      )
    )

  val usernameValid: Check[List[String], String, String] =
    lenCheck(4) andThen alnum

  def hasChar(c: Char): Check[List[String], String, String] =
    Check.Pure(
      Predicate.Pure(v =>
        if v.contains(c) then Valid(v)
        else Invalid(List(s"Must contain $c"))
      )
    )

  val emailValid: Check[List[String], String, String] =
    hasChar('@')


  println("─" * x)
  println(usernameValid("user1"))
  println(usernameValid("u"))
  println(usernameValid("user_@"))
  println("─" * x)
