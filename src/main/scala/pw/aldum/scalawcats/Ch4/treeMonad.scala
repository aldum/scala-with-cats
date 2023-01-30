package pw.aldum
package scalawcats

import cats.Monad
import Tree.*

import scala.annotation.tailrec


given Monad[Tree] = new Monad[Tree]:

  override def pure[A](a: A): Tree[A] =
    Leaf(a)

  override def flatMap[A, B](t: Tree[A])(f: A => Tree[B]): Tree[B] =
    t match
      case Leaf(a)      => f(a)
      case Branch(l, r) => Branch(flatMap(l)(f), flatMap(r)(f))

  // @tailrec
  override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] =
    f(a) match
      case Leaf(Left(l))  => tailRecM(l)(f)
      case Leaf(Right(l)) => leaf(l)
      case Branch(l, r)   =>
        Branch(???, ???)


