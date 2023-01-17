package pw.aldum
package scalawcats

@main def Main(args: String*): Unit =
  import PrintableInstances.given
  import PrintableSyntax.PrintableOps

  val fluffy = Cat("Fluffy", 3, "black")
  println("─" * 100)
  println(fluffy.format)
  fluffy.print
  println("─" * 100)
