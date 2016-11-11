class FancyGreeter(greeting: String) {
  def greet() = println(greeting)
}
val g = new FancyGreeter("Salutations, world")
g.greet()
