package pw.aldum
package scalawcats

import cats.data.Writer
import cats.syntax.writer.*
import cats.syntax.applicative.* // for pure

def slowly[A](body: => A) =
  try body finally Thread.sleep(100)

// def factorial(n: Int): Int =
//   val ans = slowly(if(n == 0) 1 else n * factorial(n - 1))
//   println(s"fact $n $ans")
//   ans

type W[A] = Writer[Vector[String], A]

def factorial(n: Int): W[Int] =
  for
    ans <-  if(n == 0)
            then
              1.pure[W]
            else
              slowly(factorial(n - 1).map(_ * n))
    _   <- Vector(s"fact $n $ans").tell
  yield ans

// def factorial(w: Int): W[Int] =
//   def go(w: W[(Int, Int)]): W[(Int, Int)] =
//     val (n, acc) = w.value
//     if n == 0 then
//       w
//     else
//     go(w.bimap(
//       l => l.appended(s"fact $n $acc"),
//       _ => (n - 1, acc * n)
//     ))
//   go(
//     (w, 1).writer(Vector.empty[String])
//   ).map((_, acc) => acc)
