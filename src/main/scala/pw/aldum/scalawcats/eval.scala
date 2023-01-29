package pw.aldum
package scalawcats

import cats.Eval

def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
  as match
    case head :: tail =>
      fn(head, foldRight(tail, acc)(fn))
    case Nil =>
      acc

def foldRightEval[A, B]
    (as: List[A], acc: B)(fn: (A, Eval[B]) => Eval[B]): Eval[B] =
  as match
    case head :: tail =>
      fn(head, Eval.defer(
        foldRightEval(tail, acc)(fn)
      ))
    case Nil =>
      Eval.now(acc)

