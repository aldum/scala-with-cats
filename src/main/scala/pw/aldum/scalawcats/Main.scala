package pw.aldum
package scalawcats

@main def Main(args: String*): Unit =
  import PrintableInstances.given

  val hello = "hello world"
  val x = 1
  println("─" * 100)
  println(Printable.format(hello))
  Printable.print(x)
  println("─" * 100)
