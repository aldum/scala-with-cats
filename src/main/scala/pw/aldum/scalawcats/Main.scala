package pw.aldum
package scalawcats

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext.Implicits.global

import cats.Traverse
import cats.Applicative
import cats.instances.list.*
import cats.instances.future.*
import cats.syntax.traverse.*



@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  val v = Vector(1, 2, 3, 4)
  object reducer extends Reducable[Int, Int]
  object stringReducer extends Reducable[Int, String]

  println("─" * x)
  println(reducer.foldMap(v)(_ + 5))
  println(reducer.foldMap(v)(i => Math.pow(2.0, i.toDouble).toInt))
  println(stringReducer.foldMap(v)(_.toString))
  println("─" * x)
