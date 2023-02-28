package pw.aldum
package scalawcats

import cats.Semigroup
import cats.data.Validated
import cats.data.Validated.*   // for Valid and Invalid
import cats.syntax.apply.*     // for mapN
import cats.syntax.semigroup.* // for |+|
import cats.syntax.validated.*

enum Predicate[E, A]:
  case Pure[E, A](
    func: A => Validated[E, A]) extends Predicate[E, A]

  case And[E, A](
    left: Predicate[E, A],
    right: Predicate[E, A]) extends Predicate[E, A]

  case Or[E, A](
    left: Predicate[E, A],
    right: Predicate[E, A]) extends Predicate[E, A]

  infix def and(that: Predicate[E, A]): Predicate[E, A] =
    And(this, that)

  infix def or(that: Predicate[E, A]): Predicate[E, A] =
    Or(this, that)

  def apply(a: A)(using Semigroup[E]): Validated[E, A] =
    this match
      case Pure(func) => func(a)
      case And(left, right) =>
        (left(a), right(a)).mapN((_, _) => a)
      case Or(left, right) =>
        left(a) match
          case Valid(_)    => Valid(a)
          case Invalid(e1) =>
            right(a) match
              case Valid(_)    => Valid(a)
              case Invalid(e2) => Invalid(e1 |+| e2)

object Predicate:
  def lift[E, A](err: E, fn: A => Boolean): Predicate[E, A] =
    Pure(a => if fn(a) then a.valid else err.invalid )
