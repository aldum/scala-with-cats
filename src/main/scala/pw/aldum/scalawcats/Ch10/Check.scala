package pw.aldum
package scalawcats

import cats.Semigroup
import cats.instances.list.* // for Semigroup
import cats.syntax.semigroup.* // for |+|
import cats.syntax.either.*

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
  case And[E, A](
    left: Check[E, A],
    right: Check[E, A]) extends Check[E, A]

  case Pure[E, A](
    func: A => Either[E, A]) extends Check[E, A]

  def and(that: Check[E, A]): Check[E, A] =
    And(this, that)

  def apply(a: A)(implicit s: Semigroup[E]): Either[E, A] =
    this match
      case Pure(func) =>
        func(a)

      case And(left, right) =>
        (left(a), right(a)) match
          case (Left(e1),  Left(e2))  => (e1 |+| e2).asLeft
          case (Left(e),   Right(_))  => e.asLeft
          case (Right(_),  Left(e))   => e.asLeft
          case (Right(_), Right(_)) => a.asRight

  def pure[E, A](f: A => Either[E, A]): Check[E, A] =
    Pure(f)
