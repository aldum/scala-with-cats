package pw.aldum
package scalawcats

import cats.Monad
import Tree.*

import scala.annotation.tailrec


given Monad[Tree] = new Monad[Tree]:

  def pure[A](a: A): Tree[A] =
    Leaf(a)

  def flatMap[A, B](t: Tree[A])(f: A => Tree[B]): Tree[B] =
    t match
      case Leaf(a)      => f(a)
      case Branch(l, r) => Branch(flatMap(l)(f), flatMap(r)(f))

  def tailRecM[A, B](arg: A)
      (func: A => Tree[Either[A, B]]): Tree[B] =
    @tailrec
    def loop(
          open: List[Tree[Either[A, B]]],
          closed: List[Option[Tree[B]]]): List[Tree[B]] =
      open match
        case Branch(l, r) :: next =>
          loop(l :: r :: next, None :: closed)

        case Leaf(Left(value)) :: next =>
          loop(func(value) :: next, closed)

        case Leaf(Right(value)) :: next =>
          loop(next, Some(pure(value)) :: closed)

        case Nil =>
          closed.foldLeft(Nil: List[Tree[B]]) { (acc, maybeTree) =>
            maybeTree.map(_ :: acc).getOrElse {
              val left :: right :: tail = acc: @unchecked
              branch(left, right) :: tail
            }
          }

    loop(List(func(arg)), Nil).head
  end tailRecM

  // def tailRecM[A, B](a: A)
  //     (f: A => Tree[Either[A, B]]): Tree[B] =
  //   flatMap( f(a) ) {
  //     case Left(value) => tailRecM(value)(f)
  //     case Right(value) => Leaf(value)
  //   }
