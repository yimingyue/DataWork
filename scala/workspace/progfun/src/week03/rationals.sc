

object rationals {
  val x = new Rational(1, 3)
  val y = new Rational(5, 7)
  val z = new Rational(3, 2)
  x.sub(y).sub(z)
  x < y
  x max y
  val strange = new Rational(3, 6)
  x < y
}

class Rational(x: Int, y: Int) {
  require(y != 0, "Denominator must be nonzero")

  def this(x: Int) = this(x, 1)
  private def gcd(a: Int, b: Int):Int = if (b == 0) a else gcd(b, a%b)

  val numer = x
  val denom = y
  def neg = new Rational(-numer, denom)
  def add(that: Rational) =
    new Rational(
      numer * that.denom + denom * that.numer, denom * that.denom
    )

  override def toString = {
    val g = gcd(x, y)
    numer/g + "/" + denom/g
  }

  def sub(that: Rational) = add(that.neg)

  def <(that: Rational) = (numer * that.denom < denom * that.numer)

  def max(that: Rational) = if (this < that ) that else this
}