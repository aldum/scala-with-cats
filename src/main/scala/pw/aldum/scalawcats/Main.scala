package pw.aldum
package scalawcats

import cats.Functor
import cats.instances.list.*   // for Functor
import cats.instances.option.* // for Functor
import cats.instances.function.* // for Functor
import cats.syntax.functor.*     // for map

@main def Main(args: String*): Unit =
  val x = 75

  val list1 = List(1, 2, 3)
  val list2 = Functor[List].map(list1)(_ * 2)

  val func = (x: Int) => x + 1
  val liftedFunc = Functor[Option].lift(func)

  val func1 = (a: Int) => a + 1
  val func2 = (a: Int) => a * 2
  val func3 = (a: Int) => s"${a}!"
  val func4 = func1.map(func2).map(func3)

  def doMath[F[_]](start: F[Int])(using Functor[F]): F[Int] =
    start.map(n => n + 1 * 2)

  println("─" * x)
  println(list1)
  println(list2)
  println(func)
  println(liftedFunc)
  println(liftedFunc(Option(1)))
  println("─" * x)
  println(func4(123))
  println("─" * x)
  println(doMath(Option(20)))
  println(doMath(List(1, 2, 3)))
  println("─" * x)
