package pw.aldum
package scalawcats

import cats.Eq
import cats.syntax.eq.*

import java.util.Date

@main def Main(args: String*): Unit =
  val fluffy = Cat("Fluffy", 3, "black")

  val eqInt = Eq[Int]
  println("─" * 100)
  println(eqInt.eqv(123, 123))
  println(eqInt.eqv(123, 120))
  println(120 === 123)
  // println(120 === "123") // Found: ("123" : String)  |  Required: Int
  println("─" * 100)
