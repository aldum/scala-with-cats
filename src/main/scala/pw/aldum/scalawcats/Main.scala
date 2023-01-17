package pw.aldum
package scalawcats

import cats.Eq
import cats.syntax.eq.*
import cats.syntax.option.*

import java.util.Date

@main def Main(args: String*): Unit =
  val fluffy = Cat("Fluffy", 3, "black")

  println("─" * 100)
  // println(Some(120) == 120) // Values of types Some[Int] and Int cannot be compared with == or !=
  // println(Some(120) === 120) // value === is not a member of Some[Int]
  // println(120 === Some(120)) // Found: Some[Int] | Required: Int
  println(1.some === none[Int])
  println("─" * 100)
