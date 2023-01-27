package pw.aldum
package scalawcats

import cats.Functor

import scala.concurrent.{Future, ExecutionContext}

// implicit val optionFunctor: Functor[Option] =
//   new Functor[Option]:
given Functor[Option] with
    def map[A, B](value: Option[A])(func: A => B): Option[B] =
      value.map(func)

// implicit def futureFunctor
//     (implicit ec: ExecutionContext): Functor[Future] =
//   new Functor[Future]:
given (using ExecutionContext): Functor[Future] with
    def map[A, B](value: Future[A])(func: A => B): Future[B] =
      value.map(func)

