package pw.aldum
package scalawcats

import cats.Applicative
import cats.instances.future.*   // for Applicative
import cats.syntax.applicative.* // for pure
import cats.syntax.apply.* // for mapN

import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global

// Combining accumulator and hostname using an Applicative:
def newCombine(accum: Future[List[Int]],
      host: String): Future[List[Int]] =
  (accum, getUptime(host)).mapN(_ :+ _)


def listTraverse[F[_]: Applicative, A, B]
      (list: List[A])(func: A => F[B]): F[List[B]] =
  list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
    (accum, func(item)).mapN(_ :+ _)
  }

def listSequence[F[_]: Applicative, B]
      (list: List[F[B]]): F[List[B]] =
  listTraverse(list)(identity)
