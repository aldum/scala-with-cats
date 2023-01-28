package pw.aldum
package scalawcats

import cats.MonadError
import cats.syntax.applicative.*      // for pure
import cats.syntax.applicativeError.* // for raiseError etc
import cats.syntax.monadError.*       // for ensure

def validateAdult[F[_]]
      (age: Int)(using me: MonadError[F, Throwable]): F[Int] =
  age.pure.ensure(new IllegalArgumentException("underage"))(_ >= 18)
  // if(age >= 18) age.pure[F]
  // else new IllegalArgumentException("Age must be greater than or equal to 18").raiseError[F, Int]