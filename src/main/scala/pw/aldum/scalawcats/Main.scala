package pw.aldum
package scalawcats

import cats.syntax.either.*
import cats.data.{ NonEmptyList, Validated }
import cats.data.Validated.{ Valid, Invalid }

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

@main def Main(args: String*): Unit =
  import scalawcats.given

  def fPrintln[A](f: Future[A]): Unit =
    println(Await.result(f, 1.second))
  val x = 75

  type Errors = NonEmptyList[String]

  def error(s: String): NonEmptyList[String] =
    NonEmptyList(s, Nil)

  def longerThan(n: Int): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be longer than $n characters"),
      str => str.size > n,
    )

  val alphanumeric: Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be all alphanumeric characters"),
      str => str.forall(_.isLetterOrDigit),
    )

  def contains(char: Char): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must contain the character $char"),
      str => str.contains(char),
    )

  def containsOnce(char: Char): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must contain the character $char only once"),
      str => str.filter(c => c == char).size == 1,
    )

  val usernameValid: Check[Errors, String, String] =
    Check.PurePredicate(
      longerThan(4) and alphanumeric
    )

  def emailValid: Check[Errors, String, String] =
    val validateAroundAt: Predicate[Errors, String] =
      Predicate.lift(
        error("e"),
        s =>
          val Array(user, domain) = s.split('@')
          val v = longerThan(0)(user) `combine`
            longerThan(2)(domain) `combine`
            containsOnce('.')(domain)
          v.isValid,
      )
    Check.PurePredicate(containsOnce('@')) andThen
      Check.PurePredicate(validateAroundAt)

  println("─" * x)
  println(usernameValid("user1"))
  println(usernameValid("u"))
  println(usernameValid("u_"))
  println(usernameValid("user_@"))
  println("─" * x)

end Main
