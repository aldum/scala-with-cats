package pw.aldum
package scalawcats

trait Printable[A]:
  def format(a: A): String

