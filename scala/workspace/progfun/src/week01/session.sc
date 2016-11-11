object session {
  1 + 2
  def abs(x: Double) = if (x < 0) -x else x

  def sqrt(x: Double) = {

    def sqrtIter(guess: Double): Double = {
      if (isGoodEnough(guess))
        guess
      else
        sqrtIter(improve(guess))
    }

    def isGoodEnough(guess: Double) = abs(guess * guess - x) < 0.001 * x

    def improve(guess: Double) = (guess + x / guess) / 2

    sqrtIter(1.0)
  }
  sqrt(2)
  sqrt(4)
  sqrt(1e-6)
  sqrt(1e60)
  def gcd(a: Int, b: Int): Int = {
    if (b == 0) a else gcd(b, a % b)
  }
  gcd(14, 21)

  def factorial(n: Int): Int = {
    def factorialIter(n: Int, v: Int):Int = {
      if (n == 1) v
      else factorialIter(n-1, n*v)
    }
    factorialIter(n, 1)
  }
  factorial(10)

  def sum(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc:Int):Int = {
      if (a > b) acc
      else loop(a+1, f(a)+acc)
    }
    loop(a, 0)
  }
  sum(x => x * x * x, 1, 4)

  def sum(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int = {
      if (a > b) 0
      else f(a) + sumF(a+1, b)
    }
    sumF
  }
  sum(x => x * x, 1, 10)
  sum(x => x, 1, 10)
  def cube: Int => Int = x => x * x * x
  sum(cube)(1, 10)


}