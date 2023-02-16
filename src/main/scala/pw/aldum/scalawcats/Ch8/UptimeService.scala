package pw.aldum
package scalawcats

import cats.instances.future.* // for Applicative
import cats.instances.list.*   // for Traverse
import cats.syntax.traverse.*  // for traverse
import scala.concurrent.ExecutionContext.Implicits.global

class UptimeService[F[_]](client: UptimeClient[F]):
  def getTotalUptime(hostnames: List[String]): F[Int] =
    // hostnames.traverse(client.getUptime).map(_.sum)
    ???
