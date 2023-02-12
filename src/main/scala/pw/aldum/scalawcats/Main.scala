package pw.aldum
package scalawcats

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  println("─" * x)

  val l = List(1, 2, 3)
  println(l.foldLeft(0)(_ - _))
  println(l.foldRight(0)(_ - _))
  println("─" * (x / 5))
  val ll: List[List[Int]] = l.map(List.apply(_))
  val nill = List.empty[List[Int]]
  println(ll.foldLeft(nill)((a, i) => i :: a))
  println(ll.foldRight(nill)((i, a) => i :: a))
  println("─" * (x / 5))
  val nil = List.empty[Int]
  println(l.foldLeft(nil)((a, i) => i :: a))
  println(l.foldRight(nil)((i, a) => i :: a))

  println("─" * x)
