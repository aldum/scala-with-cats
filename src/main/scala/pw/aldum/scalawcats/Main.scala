package pw.aldum
package scalawcats

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  def fPrintln[A](f: Future[A]): Unit =
    println(Await.result(f, 1.second))

  println("─" * x)
  println("─" * x)
