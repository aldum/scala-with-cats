package pw.aldum
package scalawcats

import cats.data.{ NonEmptyList, Validated }
import cats.data.Validated.{ Valid, Invalid }
import cats.syntax.apply.* // for mapN
import cats.syntax.validated.catsSyntaxValidatedId

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
      str => str.length > n,
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
    Check(
      longerThan(3) and alphanumeric
    )

  val splitEmail: Check[Errors, String, (String, String)] =
    Check(_.split('@') match {
      case Array(name, domain) =>
        (name, domain).validNel[String]

      case _ =>
        "Must contain a single @ character".invalidNel[(String, String)]
    })

  val checkLeft: Check[Errors, String, String] =
    Check(longerThan(0))

  val checkRight: Check[Errors, String, String] =
    Check(longerThan(3) and contains('.'))

  lazy val joinEmail: Check[Errors, (String, String), String] =
    Check {
      case (l, r) =>
        (checkLeft(l), checkRight(r)).mapN(_ + "@" + _)
    }

  def emailValid: Check[Errors, String, String] =
    splitEmail andThen joinEmail

  println("─" * x)
  println(usernameValid("user1"))
  println(usernameValid("u"))
  println(usernameValid("u_"))
  println(usernameValid("user_@"))
  println("─" * x)

end Main
