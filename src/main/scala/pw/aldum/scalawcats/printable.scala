package pw.aldum
package scalawcats

trait Printable[A]:
  def format(a: A): String

object PrintableInstances:

  given Printable[String] with
    def format(s: String): String = s

  given Printable[Int] with
    def format(i: Int): String = i.toString

object Printable:
  def format[A: Printable](a: A): String =
    summon[Printable[A]].format(a)
  def print[A: Printable](a: A): Unit =
    println(format(a))

object PrintableSyntax:
  implicit class PrintableOps[A](a: A):
    def format(using p: Printable[A]): String =
      p.format(a)

    def print(using p: Printable[A]) : Unit =
      println(a.format)

extension [A](a: A)(using p: Printable[A])
  def format: String =
    p.format(a)

  def print: Unit =
    println(a.format)