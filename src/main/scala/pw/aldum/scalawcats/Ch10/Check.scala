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

enum Check[E, A, B]:
  def apply(in: A)(using Semigroup[E]): Validated[E, B] =
    this match
      case Pure(pred)        => pred(in)
      case Map(check, f)     => check(in).map(f)
      case FlatMap(check, f) => check(in).withEither(
        e => e.flatMap(b => f(b)(in).toEither)
      )

  def map[C](f: B => C): Check[E, A, C] =
    Map[E, A, B, C](this, f)

  def flatMap[C](in: A)(f: B => Check[E, A, C]): Check[E, A, C] =
    FlatMap[E, A, B, C](this, f)

  case Map[E, A, B, C](
    check: Check[E, A, B],
    f: B => C
  ) extends Check[E, A, C]

  case FlatMap[E, A, B, C](
    check: Check[E, A, B],
    f: B => Check[E, A, C]
  ) extends Check[E, A, C]

  case Pure[E, A](
    pred: Predicate[E, A]
  ) extends Check[E, A, A]
