package cats.study

import cats.Functor
import cats.syntax.functor._

sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

final case class Leaf[A](value: A) extends Tree[A]

object Tree {

  def leaf[A](value: A): Tree[A] = Leaf(value)
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)

  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = {
      tree match {
        case Branch(left, right) => Branch(map(left)(f), map(right)(f))
        case Leaf(value) => Leaf[B](f(value))
      }
    }
  }
}

object Test {
  import Tree._
  def test(): Tree[Int] = {
    leaf(1).map(_ * 2)
    branch(leaf(1), branch(leaf(8), leaf(10))).map(_ * 2)
  }
}