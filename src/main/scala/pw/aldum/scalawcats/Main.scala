package pw.aldum
package scalawcats

import cats.Eq
import cats.syntax.eq.*

import java.util.Date

@main def Main(args: String*): Unit =
  val fluffy = Cat("Fluffy", 3, "black")
  val x = new Date() // now
  Thread.sleep(1000)
  val y = new Date() // a bit later than now

  println("─" * 100)
  println(fluffy === fluffy)
  println(x)
  println(y)
  println(x === y)
  println("─" * 100)
