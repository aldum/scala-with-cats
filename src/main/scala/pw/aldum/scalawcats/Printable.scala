package pw.aldum
package scalawcats

trait Printable[A]:
  def format(a: A): String

object PrintableInstances:

  given Printable[String] with
    def format(s: String): String = s

  given Printable[Int] with
    def format(i: Int): String = i.toString
