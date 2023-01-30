package pw.aldum
package scalawcats

import cats.data.State

import scala.util.Try
import scala.util.Failure
import scala.util.Success

import math.Integral.Implicits.infixIntegralOps

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
          val List(op1, op2) = oldStack.take(2)
          val result   = op.apply(op1, op2)
          (result :: newStack, result)
    }
