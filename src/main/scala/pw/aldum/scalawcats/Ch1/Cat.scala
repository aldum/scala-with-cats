package pw.aldum
package scalawcats

final case class Cat(
  name: String,
  age: Int,
  color: String
)

given Printable[Cat] with
  import PrintableInstances.given

  def format(cat: Cat): String =
    val Cat(name, age, color) = cat
    val n = Printable.format(name)
    val a = Printable.format(age)
    val c = Printable.format(color)
    s"$n is a $a year-old $c cat."
