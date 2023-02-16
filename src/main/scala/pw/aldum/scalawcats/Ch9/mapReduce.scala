package pw.aldum
package scalawcats

import cats.Monoid
import cats.Foldable

trait Reducable[A, B: Monoid]:
  def foldMap(seq: Vector[A])(f: A => B): B =
    val m = summon[Monoid[B]]
    seq.map(f).foldLeft(m.empty)(m.combine)
