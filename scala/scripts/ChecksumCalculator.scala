class ChecksumCalculator {
  private var sum = 0;
  def add(b: Byte) { sum += b }
  def checksum: Int = ~(sum &0xFF) + 1
}

object ChecksumCalculator {
  def calcChecksum(s: String): Int = {
    val cc = new ChecksumCalculator
    for (c <- s)
      cc.add(c.toByte)
    cc.checksum
  }
}
