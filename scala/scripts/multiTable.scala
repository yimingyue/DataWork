def multiTable = {
  val table = for (i <- 1 to 10) yield {
    val row = for (j <- 1 to 10) yield {
      val prod = (i * j).toString
      String.format("%4s", Array(prod))
    }
    row.mkString + '\n'
  }
  table.mkString
}
println(multiTable)
