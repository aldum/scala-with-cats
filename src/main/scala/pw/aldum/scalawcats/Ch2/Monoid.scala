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

// exclusive or with identity false
given booleanEitherMonoid: Monoid[Boolean] =
  new Monoid[Boolean]:
    def combine(a: Boolean, b: Boolean) =
      (a && !b) || (!a && b)

    def empty = false

// exclusive nor
given booleanXnorMonoid: Monoid[Boolean] =
  new Monoid[Boolean]:
    def combine(a: Boolean, b: Boolean) =
      (!a || b) && (a || !b)

    def empty = true

// What monoids and semigroups are there for sets?
trait SetUnionMonoid[A] extends Monoid[Set[A]]:
  def combine(x: Set[A], y: Set[A]): Set[A] = x union y
  def empty: Set[A] = Set.empty[A]

// implicit def setUnionMonoid[A]: Monoid[Set[A]] =
//   new Monoid[Set[A]] {
//     def combine(a: Set[A], b: Set[A]) = a union b
//     def empty = Set.empty[A]
//   }

given setUnionMonoid[A]: Monoid[Set[A]] =
  new Monoid[Set[A]]:
    def combine(a: Set[A], b: Set[A]) = a union b
    def empty = Set.empty[A]

// Set intersection forms a semigroup, but doesn’t form a monoid because it has
// no identity element:
trait SetIntersectionSemigroup[A] extends Semigroup[Set[A]]:
  def combine(x: Set[A], y: Set[A]): Set[A] = x intersect y

// symmetric difference (the union less the intersection) does also form a monoid
given setDiffMonoid[A]: Monoid[Set[A]] =
  new Monoid[Set[A]]:
    def combine(a: Set[A], b: Set[A]) = (a diff b) union (b diff a)
    def empty = Set.empty[A]
