package pw.aldum
package scalawcats

import cats.data.State
import cats.syntax.all.*
import cats.syntax.flatMap

import scala.util.Try
import scala.util.Failure
import scala.util.Success


object RPN:

  type CalcState[A] = State[List[Int], A]

  enum Expr:
    case Value(n: Int)
    case Op(op: Function2[Int, Int, Int])

  import Expr.*

  private def toExpr(sym: String): Option[Expr] =
    sym match
      case "+" => Some(Op(op = (x: Int, y: Int) => x + y))
      case "*" => Some(Op(op = (x: Int, y: Int) => x * y))
      case "-" => Some(Op(op = (x: Int, y: Int) => x - y))
      case "/" => Some(Op(op = (x: Int, y: Int) => x / y))
      case _ => Try(sym.toInt).toOption.map(Value.apply)

  def evalOne(sym: String): CalcState[Int] =
    State[List[Int], Int] { oldStack =>
      val symbol = toExpr(sym).getOrElse(
        throw new IllegalArgumentException("Invalid symbol")
      )
      symbol match
        case Value(n) => (n :: oldStack , n)
        case Op(op)   =>
          val newStack = oldStack.drop(2)
          // (having the second operand at the top of the stack)
          val List(op2, op1) = oldStack.take(2)
          val result   = op.apply(op1, op2)
          (result :: newStack, result)
    }

  def evalOneBook(sym: String): CalcState[Int] =
    sym match
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "*" => operator(_ * _)
      case "/" => operator(_ / _)
      case num => operand(num.toInt)

  private def operand(num: Int): CalcState[Int] =
    State[List[Int], Int] { stack =>
      (num :: stack, num)
    }

  private def operator(func: (Int, Int) => Int): CalcState[Int] =
    State[List[Int], Int] {
      case b :: a :: tail =>
        val ans = func(a, b)
        (ans :: tail, ans)

      case _ =>
        sys.error("Fail!")
    }

  def evalAll(input: List[String]): CalcState[Int] =
    input.foldLeft(State.pure(0)){ (a, b) =>
      a.flatMap(_ => evalOne(b))
    }

  def evalInput(input: String): Int =
    val in = input.split(" ").nn.map(_.nn).toList
    evalAll(in).runA(Nil).value
