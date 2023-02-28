package pw.aldum
package scalawcats

import cats.Semigroup
import cats.instances.list.* // for Semigroup
import cats.syntax.apply.*   // for mapN
import cats.syntax.semigroup.* // for |+|
import cats.syntax.either.*
import cats.data.Validated
import cats.data.Validated.*

final case class CheckF[E: Semigroup, A](func: A => Either[E, A]):
  def apply(a: A): Either[E, A] =
    func(a)

  infix def and(that: CheckF[E, A]): CheckF[E, A] =
    CheckF((a: A) =>
      (this(a), that(a)) match
        case (Right(a), Right(_)) => a.asRight
        case (Left(e1), Left(e2)) => Left(e1 |+| e2)
        case (Left(e1), Right(_)) => e1.asLeft
        case (Right(_), Left(e2)) => e2.asLeft
    )


enum Check[E, A]:
  case Pure[E, A](
    func: A => Validated[E, A]) extends Check[E, A]

  case And[E, A](
    left: Check[E, A],
    right: Check[E, A]) extends Check[E, A]

  case Or[E, A](
    left: Check[E, A],
    right: Check[E, A]) extends Check[E, A]

  infix def and(that: Check[E, A]): Check[E, A] =
    And(this, that)

  infix def or(that: Check[E, A]): Check[E, A] =
    Or(this, that)

  def apply(a: A)(implicit s: Semigroup[E]): Validated[E, A] =
    this match
      case Pure(func) =>
        func(a)
      case And(left, right) =>
        (left(a), right(a)).mapN((_, _) => a)
      case Or(left, right) =>
        left(a) match
          case Valid(a)    => Valid(a)
          case Invalid(e1) =>
            right(a) match
              case Valid(a)    => Valid(a)
              case Invalid(e2) => Invalid(e1 |+| e2)


  def pure[E, A](f: A => Validated[E, A]): Check[E, A] =
    Pure(f)
