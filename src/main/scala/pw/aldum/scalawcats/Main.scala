package pw.aldum
package scalawcats

@main def Main(args: String*): Unit =
  import scalawcats.given
  val x = 75

  val users = Map(
    1 -> "dade",
    2 -> "kate",
    3 -> "margo"
  )

  val passwords = Map(
    "dade"  -> "zerocool",
    "kate"  -> "acidburn",
    "margo" -> "secret"
  )

  val db = Db(users, passwords)


  println("─" * x)
  println(checkLogin(1, "zerocool").run(db))
  println(checkLogin(4, "davinci").run(db))
  println("─" * x)
