package pw.aldum
package scalawcats

import cats.kernel.CommutativeMonoid

trait BoundedSemiLattice[A] extends CommutativeMonoid[A]:
  def combine(a1: A, a2: A): A
  def empty: A

given BoundedSemiLattice[Int] with
  override def combine(a1: Int, a2: Int): Int =
    a1 max a2

  val empty = 0

given BoundedSemiLattice[Set[?]] with
  override def combine(a1: Set[?], a2: Set[?]): Set[?] =
    a1 concat a2

  val empty = Set.empty
