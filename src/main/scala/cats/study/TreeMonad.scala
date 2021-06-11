package cats.study

import cats.Monad

import scala.collection.mutable.ListBuffer

object TreeMonad {

  sealed trait Tree[+A]
  final case class Branch[A](left: Tree[A], right: Tree[A])
    extends Tree[A]
  final case class Leaf[A](value: A) extends Tree[A]
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)
  def leaf[A](value: A): Tree[A] =
    Leaf(value)

  val test: Seq[Int] = List(1).flatMap(List(_))
  ListBuffer(1,2,3) += 4

  implicit val treeMonad = new Monad[Tree] {
    override def pure[A](a: A): Tree[A] =
      leaf(a)

    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] =
      fa match {
        case Branch(left, right) =>
        case Leaf(value) =>
      }

    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] = ???
  }

}
