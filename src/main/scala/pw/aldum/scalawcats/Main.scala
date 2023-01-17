package pw.aldum
package scalawcats

import cats.Show
import cats.syntax.show.*

import java.util.Date

@main def Main(args: String*): Unit =
  val fluffy = Cat("Fluffy", 3, "black")

  val showInt = Show.apply[Int]
  println("─" * 100)
  println(new Date().show)
  println(fluffy.show)
  println("─" * 100)
