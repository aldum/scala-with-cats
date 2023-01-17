package pw.aldum
package scalawcats

trait Printable[A]:
  def format[A](a: A): String = a.toString

object PrintableInstances:
  given Printable[String] with
    def format(s: String): String = s

  given Printable[Int] with
    def format(i: Int): String = i.toString

object Printable:
  def format[A: Printable](a: A) = summon[Printable[A]].format(a)
  def print[A: Printable](a: A) = println(format(a))
