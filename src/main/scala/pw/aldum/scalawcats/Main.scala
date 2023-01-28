package pw.aldum
package scalawcats

import scala.util.Try

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  println("─" * x)
  println(validateAdult[Try](18)) // Try[Int] = Success(18)
  println(validateAdult[Try](8)) // Try[Int] = Failure(IllegalArgumentException)

  type ExceptionOr[A] = Either[Throwable, A]
  println(validateAdult[ExceptionOr](-1)) // ExceptionOr[Int] = Left(IllegalArgumentException)
  println("─" * x)
