package pw.aldum
package scalawcats

@main def Main(args: String*): Unit =
  import PrintableInstances.given

  println("─" * 100)
  Printable.print(Cat("Fluffy", 3, "black"))
  println("─" * 100)
