package pw.aldum
package scalawcats

trait Monad[F[_]]:
  def pure[A](a: A): F[A]

  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

  def map[A, B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(func andThen pure)
    // flatMap(value)(a => pure(func(a))) // book
