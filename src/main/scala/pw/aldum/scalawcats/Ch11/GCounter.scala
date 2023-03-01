package pw.aldum
package scalawcats

final case class GCounter(counters: Map[String, Int]):
  def increment(machine: String, amount: Int) =
    val current = counters.getOrElse(machine, 0)
    GCounter(counters.updated(machine, current + amount))

  infix def merge(that: GCounter): GCounter =
    val (present, missing) = that.counters.partition(
      (m, _) => counters.contains(m)
    )
    val merged = (counters ++ missing).collect{
      case (m, a) =>
        m -> (a max present.getOrElse(m, 0))
    }

    GCounter(merged)

  def total: Int =
    counters.values.sum
