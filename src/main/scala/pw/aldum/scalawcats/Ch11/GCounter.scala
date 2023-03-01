package pw.aldum
package scalawcats

import cats.kernel.CommutativeMonoid
import cats.syntax.semigroup.* // for |+|
import cats.syntax.foldable.*  // for combineAll


trait GCounter[F[_, _], K, V]:
  def increment(f: F[K, V])(k: K, v: V)(using m: CommutativeMonoid[V]): F[K, V]

  def merge(f1: F[K, V], f2: F[K, V])(using b: BoundedSemiLattice[V]): F[K, V]

  def total(f: F[K, V])(implicit m: CommutativeMonoid[V]): V

object GCounter:
  def apply[F[_, _], K, V](using counter: GCounter[F, K, V]) =
    counter

given GCM[K, V]: GCounter[Map, K, V] =
  new GCounter[Map, K, V]:
    override def increment(f: Map[K, V])
                          (k: K, v: V)
                          (using m: CommutativeMonoid[V]): Map[K, V] =
      val value = v |+| f.getOrElse(k, m.empty)
      f.updated(k, value)

    override def merge(f1: Map[K, V], f2: Map[K, V])
                      (using b: BoundedSemiLattice[V]): Map[K, V] =
      f1 |+| f2

    override def total(f: Map[K, V])
                      (using m: CommutativeMonoid[V]): V =
      f.values.toList.combineAll
