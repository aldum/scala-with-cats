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
