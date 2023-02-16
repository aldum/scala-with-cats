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

  println("─" * x)
  println(testTotalUptime())
  println("─" * x)
