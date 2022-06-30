package com.tutorials.scala.functional

object AdvancedPatternMatching extends App {
  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head")
    case _ => //do nothing
  }

  /*
  The only structures available for pattern matching are:
  - constants
  - wildcards
  - case cases
  - Tuples
  - Classes with companion objects that implements the unapply method (in different fashions)
  - Special elements (like above and classes that implements get and isEmpty methods, like Option)
   */

  class Person(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] = { //return the same arguments that the class Person takes
      if (person.age > 40) None
      else Some((person.name, person.age))
    }

    def unapply(age: Int): Option[String] = { // return a String with the legal status of the Person
      Some(if (age < 18) "minor" else "major")
    }
  }

  val bob = new Person("Bob", 34)
  val greeting = bob match {
    case Person("Boby", _) => "Hey Boby!"
    case Person(n, a) => s"Hi, my name is ${n} and I am $a years old"
    case _ => "Oops!"
  }

  println(greeting)

  val legalStatus = bob.age match { //uses the unapply method that takes an Int
    case Person(status) => s"My legal status is $status"
  }

  println(s"Bob's legal status is: $legalStatus")



  val n: Int = 40

  /*
  val mathProperty = n match {
    case x if x < 10 => "Single digit"
    case x if x % 2 == 0 => "an even number"
    case _ => "No property"
  }

  can be refactor to:
  */

  object singleDigit {
    def unapply(number: Int): Boolean = number < 10
  }

  object even {
    def unapply(number: Int): Boolean = number % 2 == 0
  }

  val mathProperty = n match {
    case singleDigit() => "Single Digit" //The parenthesis are required
    case even() => "An even number"
    case _ => "No property"
  }

  println(mathProperty)
}
