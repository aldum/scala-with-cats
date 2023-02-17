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
import cats.instances.vector.*        // for Foldable and Traverse

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

  def parallelFoldMapBookCats
      (values: Vector[A])
      (func: A => B)
      (using ExecutionContext): Future[B] =
    values
      .grouped(parN)
      .toVector
      .traverse(group => Future(group.toVector.foldMap(func)))
      .map(_.combineAll)

end Reducible

// TODO how to do this polimorphically?
trait ParReducable[A, B: Monoid, T[_]: Traverse]:

  // private def fold(seq: Iterable[B]): B =
  private def fold(seq: Vector[B]): B =
    val m = summon[Monoid[B]]
    seq.foldLeft(m.empty)(m.combine)

  // def foldMap(seq: Iterable[A])(f: A => B): B =
  def foldMap(seq: Vector[A])(f: A => B): B =
    fold(seq.map(f))

  def parallelFoldMap
      (values: Vector[A])
      (f: A => B): T[B] =
    // def traverse[G[_]: Applicative, A, B](fa: F[A])(f: A => G[B]): G[F[B]]
    // summon[T].traverse(
    //   // values.toVector
    //   ???
    // )(???)

    // val work = values.grouped(values.size.toInt / parN)
    // values.grouped(values.size.toInt / parN)
    //   .toVector
    //   .traverse(group => ???)
    ???
