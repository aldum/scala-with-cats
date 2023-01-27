package pw.aldum
package scalawcats

import cats.Monoid
import cats.instances.int.*
import cats.instances.option.*
import cats.syntax.semigroup.*


object `SuperAdder v3.5a-32`:
  private def combine[A](l: List[A])(using Monoid[A]): A =
    Monoid.apply[A].combineAll(l)

  def add(items: List[Int]): Int =
    combine(items)

  def add(items: List[Option[Int]]): Option[Int] =
    combine(items)
