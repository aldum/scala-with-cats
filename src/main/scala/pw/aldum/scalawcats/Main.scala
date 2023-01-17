package pw.aldum
package scalawcats

import cats.Eq
import cats.syntax.eq.*

@main def Main(args: String*): Unit =
  val cat1 = Cat("Garfield",   38, "orange and black")
  val cat2 = Cat("Heathcliff", 33, "orange and black")
  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]

  println("─" * 100)
  println(cat1 === cat2)
  println(optionCat1 === optionCat2)
  println("─" * 100)
