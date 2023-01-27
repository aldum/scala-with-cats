package pw.aldum
package scalawcats

import cats.Functor

import scala.concurrent.{Future, ExecutionContext}

// implicit val optionFunctor: Functor[Option] =
//   new Functor[Option]:
given Functor[Option] with
  def map[A, B](value: Option[A])(func: A => B): Option[B] =
    value.map(func)

// implicit def futureFunctor
//     (implicit ec: ExecutionContext): Functor[Future] =
//   new Functor[Future]:
given (using ExecutionContext): Functor[Future] with
  def map[A, B](value: Future[A])(func: A => B): Future[B] =
    value.map(func)


// 3.5.4
given Functor[Tree] with
  import Tree.*
  def map[A, B](fa: Tree[A])(f: A => B): Tree[B] =
    fa match
      case Leaf(a)      => Leaf(f(a))
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
