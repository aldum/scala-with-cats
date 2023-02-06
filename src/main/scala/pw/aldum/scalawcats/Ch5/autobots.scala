package pw.aldum
package scalawcats

import cats.data.EitherT
import cats.syntax.applicative.*
import cats.instances.future.*
import cats.instances.either.*

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// type Response[A] = Future[Either[String, A]]
type Response[A] = EitherT[Future, String, A]

val powerLevels = Map(
  "Jazz"      -> 6,
  "Bumblebee" -> 8,
  "Hot Rod"   -> 10
)

def getPowerLevel(autobot: String): Response[Int] =
  EitherT(Future(
    powerLevels.get(autobot) match
      case Some(v) => Right(v)
      case None    => Left("not found")
  ))

def canSpecialMove(ally1: String, ally2: String): Response[Boolean] =
  val power = for
    p1 <- getPowerLevel(ally1)
    p2 <- getPowerLevel(ally2)
  yield p1 + p2
  power.bimap(e => s"power error: $e", _ >= 15)
