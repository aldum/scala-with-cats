package pw.aldum
package scalawcats

import cats.data.Reader

type DbReader[A] = Reader[Db, A]

final case class Db(
  usernames: Map[Int, String],
  passwords: Map[String, String]
)

def findUsername(userId: Int): DbReader[Option[String]] =
  Reader(db => db.usernames.get(userId))

def checkPassword(
      username: String,
      password: String): DbReader[Boolean] =
  Reader(db =>
    db.passwords
      .get(username)
      .exists(_ == password)
  )

def checkLogin(
      userId: Int,
      password: String): DbReader[Boolean] =
  findUsername(userId).flatMap{
    case None => Reader[Db, Boolean](_ => false)
    case Some(username) =>
      checkPassword(username, password)
  }
