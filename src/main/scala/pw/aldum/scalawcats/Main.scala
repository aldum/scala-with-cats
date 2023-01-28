package pw.aldum
package scalawcats

import cats.syntax.either.* // for asRight

def countPositive(nums: List[Int]) =
  // nums.foldLeft(Right(0)) { (accumulator, num) =>
  nums.foldLeft(0.asRight) { (accumulator, num) =>
    if(num > 0)
      accumulator.map(_ + 1)
    else
      Left("Negative. Stopping!")
  }

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  println("─" * x)
  println(Symbol("asd"))
  println("─" * x)
