package pw.aldum
package scalawcats

import cats.syntax.monoid.*

final case class GCounter[V](counters: Map[String, V])(using bsl: BoundedSemiLattice[V]):
  def increment(machine: String, amount: V) =
    val value = bsl.combine(
      counters.getOrElse(machine, bsl.empty),
      amount,
    )
    GCounter(counters.updated(machine, value))

  infix def merge(that: GCounter[V]): GCounter[V] =
    val merged = that.counters ++ counters.map {
      case (m, a) =>
        m -> bsl.combine(a, that.counters.getOrElse(m, bsl.empty))
    }

    GCounter(merged)

  def total: V =
    counters.values.fold(bsl.empty)(bsl.combine)
