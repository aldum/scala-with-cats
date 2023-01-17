package pw.aldum
package scalawcats

import cats.Eq
import cats.syntax.eq.*

import java.util.Date

given dateEq: Eq[Date] =
  Eq.instance[Date] { (date1, date2) =>
    date1.getTime === date2.getTime
  }

given catEq: Eq[Cat] =
  Eq.instance[Cat] { (cat1, cat2) =>
    cat1 == cat2 // it's a case class :)
  }