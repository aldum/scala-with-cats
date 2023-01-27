package pw.aldum
package scalawcats

import cats.Functor
import cats.instances.list.*   // for Functor
import cats.instances.option.* // for Functor
import cats.instances.function.* // for Functor
import cats.syntax.functor.*     // for map

@main def Main(args: String*): Unit =
  import scalawcats.PrintableInstances.given
  val x = 75

  println("─" * x)
  println(format(Box("hello world")))
  println(format(Box(true)))
  println("─" * x)
