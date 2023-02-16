package pw.aldum
package scalawcats

import cats.Monoid
import cats.Foldable

import scala.concurrent.Future
import scala.concurrent.ExecutionContext

trait Reducable[A, B: Monoid]:
  lazy val parN = Runtime.getRuntime.nn.availableProcessors

  private def fold(seq: Vector[B]): B =
    val m = summon[Monoid[B]]
    seq.foldLeft(m.empty)(m.combine)


  def foldMap(seq: Vector[A])(f: A => B): B =
    fold(seq.map(f))

  def parallelFoldMap
    (values: Vector[A])
    (f: A => B)
    (using ExecutionContext): Future[B] =
      val work = values.grouped(values.length / parN)

      Future.traverse(work)(v => Future(
        foldMap(v)(f)
      )).map(r => fold(r.toVector))

