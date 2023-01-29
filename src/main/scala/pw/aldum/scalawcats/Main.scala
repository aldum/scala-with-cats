package pw.aldum
package scalawcats

import cats.Eval
import cats.syntax.option.*

import scala.util.Random

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  def sum(l: List[Int]) = foldRightEval(l, 0){
    (i, accE) =>
      accE.map(_ + i)
  }
  def max(l: List[Int]) = foldRightEval(l, none[Int]){
    (i, accE) =>
      accE.map{
        case None => Some(i)
        case Some(m) => Some( Math.max(i, m) )
      }
  }

  println("─" * x)
  println(sum(List(1,2,3)).value)
  println(sum((1 to 1500).toList).value)
  println(max(List(10,20,4)).value)
  println(max(List.fill(10000)(Random.nextInt())).value)
  println("─" * x)
