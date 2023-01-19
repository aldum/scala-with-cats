package pw.aldum
package scalawcats

trait Semigroup[A]:
  def combine(x: A, y: A): A

trait Monoid[A] extends Semigroup[A]:
  def empty: A

object Monoid:
  def apply[A](implicit monoid: Monoid[A]) =
    monoid

// Consider Boolean. How many monoids can you define for this type?
object BoolAndMonoid extends Monoid[Boolean]:
  def combine(x: Boolean, y: Boolean): Boolean = x && y
  def empty: Boolean = true

object BoolOrMonoid extends Monoid[Boolean]:
  def combine(x: Boolean, y: Boolean): Boolean = x || y
  def empty: Boolean = false