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

  val totalUptime: Future[List[Int]] =
    Traverse[List].traverse(hostnames)(getUptime)

  val numbers = List(Future(1), Future(2), Future(3))
  val numbers2: Future[List[Int]] =
    Traverse[List].sequence(numbers)

  println("─" * x)
  println(totalUptime)
  println(Await.result(numbers2, 1.second))

  // doesn't compile, future instances deprecated
  // Await.result(hostnames.traverse(getUptime), 1.second)
  // Await.result(numbers.sequence, 1.second)
  println("─" * x)
