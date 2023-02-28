package pw.aldum
package scalawcats

import cats.data.Kleisli
import cats.data.{ NonEmptyList, Validated }
import cats.data.Validated.{ Valid, Invalid }
import cats.syntax.apply.* // for mapN
import cats.syntax.validated.catsSyntaxValidatedId

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import cats.data.ValidatedNel

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

  type Result[A]   = Either[Errors, A]
  type Check[A, B] = Kleisli[Result, A, B]

  // Create a check from a function:
  def check[A, B](func: A => Result[B]): Check[A, B] =
    Kleisli(func)

  // Create a check from a Predicate:
  def checkPred[A](pred: Predicate[Errors, A]): Check[A, A] =
    Kleisli[Result, A, A](pred.run)

  // val validateUsername: Kleisli[[B] =>> Either[Errors, B], String, String] =
  val validateUsername: Kleisli[[B] =>> Result[B], String, String] =
    Kleisli(longerThan(3).run) `andThen` Kleisli(alphanumeric.run)

  val splitEmail: String => Result[(String, String)] =
    (v: String) =>
      v.split('@') match
        case Array(name, domain) =>
          (name, domain).validNel[String].toEither
        case _ =>
          "Must contain a single @ character"
            .invalidNel[(String, String)]
            .toEither

  val checkLeft =
    Kleisli(longerThan(0).run)

  val checkRight =
    Kleisli((longerThan(3) and contains('.')).run)

  val joinEmail: ((String, String)) => Result[String] =
    (l, r) => (checkLeft(l), checkRight(r)).mapN(_ + "@" + _)

  val validateEmail: Kleisli[[B] =>> Either[Errors, B], String, String] =
    check(splitEmail) `andThen` check(joinEmail)

  println("─" * x)
  println(validateUsername("user1"))
  println(validateUsername("u"))
  println(validateUsername("u_"))
  println(validateUsername("user_@"))
  println(validateEmail("email"))
  println(validateEmail("user_@"))
  println(validateEmail("u@a"))
  println(validateEmail("u@a.a"))
  println(validateEmail("user@abc"))
  println(validateEmail("user@dom.at"))
  println("─" * x)

end Main
