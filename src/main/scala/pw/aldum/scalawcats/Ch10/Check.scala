package pw.aldum
package scalawcats

import cats.Semigroup
import cats.instances.list.*   // for Semigroup
import cats.syntax.apply.*     // for mapN
import cats.syntax.semigroup.* // for |+|
import cats.data.Validated
import cats.data.Validated.{ Valid, Invalid }

enum Check[E, A, B]:
  def apply(in: A)(using Semigroup[E]): Validated[E, B] =
    this match
      case Pure(f)          => f(in)
      case PurePredicate(p) => p(in)
      case Map(check, f)    => check(in).map(f)
      case FlatMap(check, f) =>
        check(in).withEither(
          _.flatMap(b => f(b)(in).toEither)
        )
      case AndThen(ch, that) =>
        ch(in).withEither(
          _.flatMap(b => that(b).toEither)
        )

  infix def map[C](f: B => C): Check[E, A, C] =
    Map[E, A, B, C](this, f)

  infix def flatMap[C](in: A)(f: B => Check[E, A, C]): Check[E, A, C] =
    FlatMap[E, A, B, C](this, f)

  infix def andThen[C](that: Check[E, B, C]): Check[E, A, C] =
    AndThen[E, A, B, C](this, that)

  case Map[E, A, B, C](
      check: Check[E, A, B],
      f: B => C,
    ) extends Check[E, A, C]

  case FlatMap[E, A, B, C](
      check: Check[E, A, B],
      f: B => Check[E, A, C],
    ) extends Check[E, A, C]

  case AndThen[E, A, B, C](
      check: Check[E, A, B],
      that: Check[E, B, C],
    ) extends Check[E, A, C]

  case Pure[E, A, B](
      func: A => Validated[E, B]
    ) extends Check[E, A, B]

  case PurePredicate[E, A](
      pred: Predicate[E, A]
    ) extends Check[E, A, A]

object Check:
  def apply[E, A, B](f: A => Validated[E, B]): Check[E, A, B] =
    Pure(f)

  def apply[E, A](pred: Predicate[E, A]): Check[E, A, A] =
    PurePredicate(pred)
