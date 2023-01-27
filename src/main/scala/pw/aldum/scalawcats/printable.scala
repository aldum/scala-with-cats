package pw.aldum
package scalawcats

trait Printable[A]:
  def format(a: A): String

  def contramap[B](func: B => A): Printable[B] =
    new Printable[B]:
      def format(value: B): String =
        val pa = summon[Printable[A]]
        Printable.format(func(value))(using pa)

object PrintableInstances:

  given Printable[String] with
    def format(s: String): String = s

  given Printable[Int] with
    def format(i: Int): String = i.toString

  // implicit val booleanPrintable: Printable[Boolean] =
    // new Printable[Boolean]:
  given Printable[Boolean] with
    def format(value: Boolean): String =
      if (value) "yes" else "no"

  given [A](using p: Printable[A]): Printable[Box[A]] with
    def format(a: Box[A]): String =
      p.contramap((box: Box[A]) => box.value).format(a)



object Printable:
  def format[A: Printable](a: A): String =
    summon[Printable[A]].format(a)
  def print[A: Printable](a: A): Unit =
    println(format(a))

object PrintableSyntax:
  implicit class PrintableOps[A](a: A):
    def format(using p: Printable[A]): String =
      p.format(a)

    def print(using Printable[A]) : Unit =
      println(a.format)

extension [A](a: A)(using p: Printable[A])
  def format: String =
    p.format(a)

  def print: Unit =
    println(a.format)

