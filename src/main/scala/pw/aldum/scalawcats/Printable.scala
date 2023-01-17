package pw.aldum
package scalawcats

trait Printable[A]:
  def format[A](a: A): String
