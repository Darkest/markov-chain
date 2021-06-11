package cats.study

trait Printable[A] { self =>
  def format(value: A): String
  def contramap[B](f: B => A) : Printable[B] =
    new Printable[B] {
      override def format(value: B): String =
        self.format(f(value))
    }
}

object PrintableExample {

  final case class Box[A](value: A)

  implicit val stringPrintable =
    new Printable[String] {
      override def format(value: String): String = s"'$value'"
    }

  implicit val boolPrintable =
    new Printable[Boolean] {
      override def format(value: Boolean): String = if (value) "yes" else "no"
    }

  implicit def boxPrintable[A](implicit printable: Printable[A]): Printable[Box[A]] = {
    printable.contramap[Box[A]](_.value)
  }

  def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)

  def test(): Unit = {
    format("Hello")
    format(true)

    format(Box("true"))
  }

}
