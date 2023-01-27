package pw.aldum
package scalawcats

trait Codec[A]:
  def encode(value: A): String
  def decode(value: String): A
  def imap[B](dec: A => B, enc: B => A): Codec[B] = ???

def encode[A](value: A)(using c: Codec[A]): String =
  c.encode(value)

def decode[A](value: String)(using c: Codec[A]): A =
  c.decode(value)
