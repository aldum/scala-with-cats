package pw.aldum
package scalawcats

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  val v = Vector.tabulate(100)(identity)
  object reducer extends Reducible[Int, Int]
  object stringReducer extends Reducible[Int, String]

  def fPrintln[A](f: Future[A]): Unit =
    println(Await.result(f, 1.second))

  println("─" * x)
  fPrintln(reducer.parallelFoldMap(v)(_ + 5))
  fPrintln(reducer.parallelFoldMap(v.take(20))(i => Math.pow(2.0, i.toDouble).toInt))
  fPrintln(stringReducer.parallelFoldMap(v)(_.toString))
  println("─" * x)
