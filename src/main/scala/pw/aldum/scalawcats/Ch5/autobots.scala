package pw.aldum
package scalawcats

import cats.data.EitherT

import scala.concurrent.Future

// type Response[A] = Future[Either[String, A]]
type Response[A] = EitherT[Future, String, A]

def getPowerLevel(autobot: String): Response[Int] =
  ???
