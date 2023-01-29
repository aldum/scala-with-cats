package pw.aldum
package scalawcats

import cats.data.Writer
import cats.syntax.writer.*

def slowly[A](body: => A) =
  try body finally Thread.sleep(100)

// def factorial(n: Int): Int =
//   val ans = slowly(if(n == 0) 1 else n * factorial(n - 1))
//   println(s"fact $n $ans")
//   ans

type W[A] = Writer[Vector[String], A]

def factorial(w: Int): W[Int] =
  def go(w: W[(Int, Int)]): W[(Int, Int)] =
    val (n, acc) = w.value
    if n == 0 then
      w
    else
    go(w.bimap(
      l => l.appended(s"fact $n $acc"),
      _ => (n - 1, acc * n)
    ))
  go(
    (w, 1).writer(Vector.empty[String])
  ).map((_, acc) => acc)
