package pw.aldum
package scalawcats

type Id[A] = A

given Monad[Id] with
  def pure[A](a: A): Id[A] = a

  def flatMap[A, B](value: Id[A])(func: A => Id[B]): Id[B] =
    func(value)

  override def map[A, B](value: Id[A])(func: A => B): Id[B] =
    func(value)
