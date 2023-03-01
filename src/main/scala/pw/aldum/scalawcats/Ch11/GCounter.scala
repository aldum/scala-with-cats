package pw.aldum
package scalawcats

import cats.kernel.CommutativeMonoid
import cats.syntax.semigroup.* // for |+|
import cats.syntax.foldable.*  // for combineAll

final case class GCounter[V](counters: Map[String, V]):
  def increment(machine: String, amount: V)(using m: CommutativeMonoid[V]) =
    val value = amount |+| counters.getOrElse(machine, m.empty)
    GCounter(counters.updated(machine, value))

  infix def merge(that: GCounter[V])(using bsl: BoundedSemiLattice[V]): GCounter[V] =
    GCounter(counters |+| that.counters)

  def total(using m: CommutativeMonoid[V]): V =
    counters.values.toList.combineAll
