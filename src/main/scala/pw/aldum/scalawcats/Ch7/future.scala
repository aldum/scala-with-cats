package pw.aldum
package scalawcats

import scala.concurrent.*
import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext.Implicits.global

val hostnames = List(
  "alpha.example.com",
  "beta.example.com",
  "gamma.demo.com"
)

def getUptime(hostname: String): Future[Int] =
  Future(hostname.length * 60) // just for demonstration

def allUptimes: Future[List[Int]] =
  listTraverse(hostnames)(getUptime)
  // hostnames.foldLeft(Future(List.empty[Int])) (
  //   (accum, host) =>
  //     val uptime = getUptime(host)
  //     for
  //       accum  <- accum
  //       uptime <- uptime
  //     yield accum :+ uptime
  // )
