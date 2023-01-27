package pw.aldum
package scalawcats

import cats.Eq
import cats.syntax.eq.*

@main def Main(args: String*): Unit =
  println("─" * 100)
  println(`SuperAdder v3.5a-32`.add(List(1,2,3,4)))
  println("─" * 100)
