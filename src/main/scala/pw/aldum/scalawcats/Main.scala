package pw.aldum
package scalawcats

@main def Main(args: String*): Unit =
  import PrintableInstances.given

  val hello = "hello world"
  val x = 1
  println("─" * 100)
  println(summon[Printable[String]].format(hello))
  println(summon[Printable[Int]].format(x))
  println("─" * 100)
