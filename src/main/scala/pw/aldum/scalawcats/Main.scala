package pw.aldum
package scalawcats

import cats.Eq
import cats.syntax.eq.*

@main def Main(args: String*): Unit =
  val orEmpty  = BoolOrMonoid.empty
  val orC      = BoolOrMonoid.combine
  val andEmpty = BoolAndMonoid.empty
  val andC     = BoolAndMonoid.combine

  println("─" * 100)
  println(orC(true, orEmpty) == true && orC(orEmpty, true) == true )
  println(orC(false, orEmpty) == false && orC(orEmpty, false) == false )

  println(andC(true, andEmpty) == true && andC(andEmpty, true) == true )
  println(andC(false, andEmpty) == false && andC(andEmpty, false) == false )

  println("─" * 100)
