package pw.aldum
package scalawcats

import cats.Eq
import cats.syntax.eq.*

@main def Main(args: String*): Unit =
  val x = 75
  val o1 = Order(10.5, 2)
  val o2 = Order(65, 1)

  println("─" * x)
  println(`SuperAdder v3.5a-32`.combine(List(o1,o2)))
  println("─" * x)
