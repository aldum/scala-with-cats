package pw.aldum
package scalawcats

import cats.Show
import cats.implicits.toShow

import java.util.Date

given Show[Date] =
  Show.show(date => s"${date.getTime}ms since the epoch.")
  // new Show[Date]:
  //   def show(date: Date): String =
  //     s"${date.getTime}ms since the epoch."

given Show[Cat] =
  Show.show((cat: Cat) => {
    val Cat(name, age, color) = cat
    val n = name.show
    val a = age.show
    val c = color.show
    s"$n is a $a year-old $c cat."
  })
  // new Show[Cat]:
  //   def show(cat: Cat): String =
  //     val Cat(name, age, color) = cat
  //     val n = name.show
  //     val a = age.show
  //     val c = color.show
  //     s"$n is a $a year-old $c cat."

