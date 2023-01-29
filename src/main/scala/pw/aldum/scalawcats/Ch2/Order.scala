package pw.aldum
package scalawcats

import cats.Monoid

final case class Order(
  totalCost: Double,
  quantity: Double
)

object Order:
  given Monoid[Order] with
    override def empty = Order(0, 0)
    override def combine(x: Order, y: Order): Order =
      Order(x.totalCost + y.totalCost,
            x.quantity + y.quantity)



