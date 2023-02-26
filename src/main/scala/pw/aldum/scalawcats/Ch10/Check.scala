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
