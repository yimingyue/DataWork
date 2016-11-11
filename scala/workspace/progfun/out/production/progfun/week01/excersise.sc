object excersise {
  def product(f: Int => Int)(a:Int, b: Int): Int = {
    if (a > b) 1
    else f(a) * product(f)(a+1, b)
  }
  product(x => x * x)(3, 4)
  def factorial(n: Int): Int = product(x => x)(1, n)
  factorial(6)
  def generalize(f: Int => Int)(acc: Int)(oper: (Int, Int) => Int)(a: Int, b: Int): Int = {
    if (a > b) acc
    else generalize(f)(oper(acc, f(a)))(oper)(a+1, b)
  }
  def product = generalize(x => x * x)(1)((x, y) => x * y)
  def sum = generalize(x => x)(0)((x, y) => x + y)
}