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
      ans <- evalOne("-")
    yield ans
  println(program.runA(Nil).value)
  val p2 = evalAll(List("1", "2", "+", "3", "*"))
  println(p2.runA(Nil).value)
  val biggerProgram = for
    _   <- evalAll(List("1", "2", "+"))
    _   <- evalAll(List("3", "4", "+"))
    ans <- evalOne("*")
  yield ans
  println(biggerProgram.runA(Nil).value)
  println("─" * x)
