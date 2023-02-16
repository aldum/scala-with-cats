package pw.aldum
package scalawcats

import cats.Monoid
import cats.Applicative
import cats.Traverse
import cats.syntax.functor.*      // for map
import cats.syntax.foldable.*
import cats.syntax.traverse.*
import cats.syntax.semigroup.*
import cats.syntax.applicative.*      // for pure

import scala.concurrent.Future
import scala.concurrent.ExecutionContext

lazy val parN = Runtime.getRuntime.nn.availableProcessors

trait Reducible[A, B: Monoid]:

  private def fold(seq: Iterable[B]): B =
    val m = summon[Monoid[B]]
    seq.foldLeft(m.empty)(m.combine)


  def foldMap(seq: Iterable[A])(f: A => B): B =
    fold(seq.map(f))

  def parallelFoldMap
      (values: Iterable[A])
      (f: A => B)
      (using ExecutionContext): Future[B] =
    val work = values.grouped(values.size / parN)

    Future.traverse(work)(v => Future(
      foldMap(v)(f)
    )).map(r => fold(r.toVector))

  def parallelFoldMapBook
      (values: Vector[A])
      (func: A => B)
      (using ExecutionContext): Future[B] =
    val numCores  = Runtime.getRuntime.nn.availableProcessors
    val groupSize = (1.0 * values.size / numCores).ceil.toInt

    // Create one group for each CPU:
    val groups: Iterator[Vector[A]] =
      values.grouped(groupSize)

    // Create a future to foldMap each group:
    val futures: Iterator[Future[B]] =
      groups map { group =>
        Future {
          group.foldLeft(Monoid[B].empty)(_ |+| func(_))
        }
      }

    // foldMap over the groups to calculate a final result:
    Future.sequence(futures) map { iterable =>
      iterable.foldLeft(Monoid[B].empty)(_ |+| _)
    }

end Reducible
