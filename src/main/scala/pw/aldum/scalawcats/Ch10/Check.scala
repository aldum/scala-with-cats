package pw.aldum
package scalawcats

import cats.Semigroup
import cats.instances.list.* // for Semigroup
import cats.syntax.apply.*   // for mapN
import cats.syntax.semigroup.* // for |+|
import cats.syntax.either.*
import cats.data.Validated
import cats.data.Validated.{Valid, Invalid}

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

sealed trait Check[E, A, B]:
  import Check.*
  def apply(a: A)(using Semigroup[E]): Validated[E, B]
  def map[C](f: B => C): Check[E, A, C] =
    Map[E, A, B, C](this, f)

object Check:
  final case class Map[E, A, B, C](
    check: Check[E, A, B],
    f: B => C
  ) extends Check[E, A, C]:
    def apply(in: A)(using Semigroup[E]): Validated[E, C] =
      check(in).map(f)

  final case class Pure[E, A](
    pred: Predicate[E, A]
  ) extends Check[E, A, A]:
    def apply(in: A)(using Semigroup[E]): Validated[E, A] =
      pred(in)

  def apply[E, A](pred: Predicate[E, A]): Check[E, A, A] =
    Pure(pred)
