package pw.aldum
package scalawcats

import cats.Applicative
import cats.syntax.functor.*   // for map
import cats.syntax.traverse.*  // for traverse

// class UptimeService[F[_]](client: UptimeClient[F])(using Applicative[F]):
class UptimeService[F[_]: Applicative](client: UptimeClient[F]):
  def getTotalUptime(hostnames: List[String]): F[Int] =
    hostnames.traverse(client.getUptime).map(_.sum)
