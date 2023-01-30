package pw.aldum
package scalawcats

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  import RPN.*

  println("─" * x)
  println(evalOne("42").runA(Nil).value)
  val program =
    for
      _   <- evalOne("1")
      _   <- evalOne("2")
      ans <- evalOne("+")
    yield ans
  println(program.runA(Nil).value)
  println("─" * x)
