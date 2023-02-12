package pw.aldum
package scalawcats

import scala.concurrent.Await
import scala.concurrent.duration.*


@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  println("─" * x)

  println(Await.result(allUptimes, 1.second))

  println("─" * x)
