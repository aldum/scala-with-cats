package pw.aldum.scalawcats

// sealed trait Tree[+A]

// final case class Branch[A](left: Tree[A], right: Tree[A])
//   extends Tree[A]

// final case class Leaf[A](value: A) extends Tree[A]

enum Tree[+A]:
  case Branch(left: Tree[A], right: Tree[A])
  case Leaf(value: A)


def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
  Tree.Branch(left, right)

def leaf[A](value: A): Tree[A] =
  Tree.Leaf(value)
