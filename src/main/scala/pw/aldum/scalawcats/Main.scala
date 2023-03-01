package pw.aldum
package scalawcats

import cats.instances.int.* // for Monoid

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

@main def Main(args: String*): Unit =
  import scalawcats.given

  def fPrintln[A](f: Future[A]): Unit =
    println(Await.result(f, 1.second))
  val x = 75

  val g1 = Map("a" -> 7, "b" -> 3)
  val g2 = Map("a" -> 2, "b" -> 5)

  val counter = GCounter[Map, String, Int]

  val merged = counter.merge(g1, g2)
  // merged: Map[String, Int] = Map("a" -> 7, "b" -> 5)
  val total  = counter.total(merged)
  // total: Int = 12

  println("─" * x)
  println(merged)
  println(total) // actually 7
  println("─" * x)

end Main
