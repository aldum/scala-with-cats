package pw.aldum
package scalawcats

// implicit val stringCodec: Codec[String] =
//   new Codec[String]:
given Codec[String] with
  def encode(value: String): String = value
  def decode(value: String): String = value

// implicit val intCodec: Codec[Int] =
given Codec[Int] =
  summon[Codec[String]].imap(_.toInt, _.toString)

// implicit val booleanCodec: Codec[Boolean] =
given Codec[Boolean] =
  summon[Codec[String]].imap(_.toBoolean, _.toString)
