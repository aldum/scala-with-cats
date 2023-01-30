package pw.aldum
package scalawcats

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  import RPN.*

  println("─" * x)
  println(evalInput("1 2 + 3 4 + *"))
  println("─" * x)
