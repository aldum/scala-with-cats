package pw.aldum
package scalawcats

// implicit val stringCodec: Codec[String] =
//   new Codec[String]:
given stringCodec: Codec[String] with
  def encode(value: String): String = value
  def decode(value: String): String = value

// implicit val intCodec: Codec[Int] =
given Codec[Int] =
  stringCodec.imap(_.toInt, _.toString)

// implicit val booleanCodec: Codec[Boolean] =
given Codec[Boolean] =
  stringCodec.imap(_.toBoolean, _.toString)
