package pw.aldum
package scalawcats

import cats.Semigroup
import cats.instances.list.* // for Semigroup
import cats.syntax.semigroup.* // for |+|

trait Check[E, A]:
  def apply(value: A): Either[E, A]
  def and(that: Check[E, A]): Check[E, A]

final case class CheckF[E: Semigroup, A](func: A => Either[E, A]) extends Check[E, A]:
  def apply(a: A): Either[E, A] =
    func(a)

  def and(that: Check[E, A]): Check[E, A] =
    CheckF((a: A) =>
      (this.apply(a), that.apply(a)) match
        case (Right(a), Right(_)) => Right(a)
        case (Left(e1), Left(e2)) => Left(e1 |+| e2)
        case (Left(e1), Right(_)) => Left(e1)
        case (Right(_), Left(e2)) => Left(e2)
    )
