package cats.study

trait Codec[A] {self =>
  def encode(value: A): String
  def decode(value: String): A
  def imap[B](dec: A => B, enc: B => A): Codec[B] =
    new Codec[B] {
      override def encode(value: B): String = self.encode(enc(value))
      override def decode(value: String): B = dec(self.decode(value))
    }
}

object Codec {

  final case class Box[A](value: A)

  def encode[A](value: A)(implicit codec: Codec[A]) = codec.encode(value)
  def decode[A](value: String)(implicit codec: Codec[A]) = codec.decode(value)


  implicit val stringCodec =
    new Codec[String] {
      override def encode(value: String): String = value
      override def decode(value: String): String = value
    }

  implicit val intCodec: Codec[Int] = stringCodec.imap(_.toInt, _.toString)
  implicit val boolCodec: Codec[Boolean] = stringCodec.imap(_.toBoolean, _.toString)
  implicit val doubleCodec: Codec[Double] = stringCodec.imap(_.toDouble, _.toString)

  implicit def boxCodec[A](implicit codec: Codec[A]): Codec[Box[A]] =
    codec.imap(Box(_), _.value)
}
