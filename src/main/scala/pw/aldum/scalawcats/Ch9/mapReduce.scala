package pw.aldum
package scalawcats

import cats.Monoid

trait Reducable[A, B: Monoid]:
  def foldMap(seq: Vector[A])(f: A => B): B
