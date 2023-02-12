package pw.aldum
package scalawcats

import cats.Foldable
import math.Numeric.Implicits.infixNumericOps

// Prove this to yourself by implementing substitutes for List's map, flatMap,
// filter, and sum methods in terms of foldRight
extension [A](l: List[A])(using n: Numeric[A])

  def foldMap[B](f: A => B): List[B] =
    l.foldRight(List.empty[B])(
      (i, a) => f(i) :: a
    )

  def foldFlatMap[B](f: A => List[B]) =
    l.foldRight(List.empty[B])(
      (i, a) => f(i) ::: a
    )

  def foldFilter(p: A => Boolean): List[A] =
    l.foldRight(List.empty[A])( (i, a) =>
      if p(i)
      then i :: a
      else a
    )

  def foldSum = l.foldRight(n.zero)(_ + _)
