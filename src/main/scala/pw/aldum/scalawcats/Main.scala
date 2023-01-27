package pw.aldum
package scalawcats

import cats.Functor
import cats.instances.list.*   // for Functor
import cats.instances.option.* // for Functor
import cats.instances.function.* // for Functor
import cats.syntax.functor.*     // for map

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  given dc: Codec[Double] =
    summon[Codec[String]].imap(_.toDouble, _.toString)
  given Codec[Box[Double]] =
    dc.imap(Box.apply, (b: Box[Double]) => b.value)

  println("─" * x)
  println(encode(3.14))
  println(decode[Double]("2.71"))

  println(encode(Box(123.4)))
  println(decode[Box[Double]]("123.4"))
  println("─" * x)
