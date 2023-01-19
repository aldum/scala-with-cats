package pw.aldum
package scalawcats

import cats.Eq
import cats.syntax.eq.*

@main def Main(args: String*): Unit =
  object intSetM extends SetUnionMonoid[Int]
  val sue = intSetM.empty
  val set1 = Set(1, 2, 3)
  val set2 = Set(3, 5)
  val set3 = Set(4, 10)
  val app = intSetM.combine
  println("─" * 100)
  println(app(set1, sue))
  println(app(sue, set1))
  println(app(set1, app(set2, set3)) == app(app(set1, set2), set3))
  println("─" * 100)
