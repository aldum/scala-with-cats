package pw.aldum
package scalawcats

import cats.Id
import cats.syntax.applicative.*

import scala.concurrent.Future

trait UptimeClient[F[_]]:
  def getUptime(hostname: String): F[Int]

trait RealUptimeClient extends UptimeClient[Future]:
  def getUptime(hostname: String): Future[Int]

// trait TestUptimeClient extends UptimeClient[Id]:
//   def getUptime(hostname: String): Id[Int] // Int


class TestUptimeClient(hosts: Map[String, Int]) extends UptimeClient[Id]:
  def getUptime(hostname: String): Int =
    hosts.getOrElse(hostname, 0)

