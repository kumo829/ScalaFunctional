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

  //Infix patterns
  case class Or[A,B](a: A, b: B) //Either

  val either = Or(2, "two")
  val humanDescription = either match {
//    case Or(number, string) => s"$number is written as $string"
    case number Or string => s"$number is written as $string" //same meaning than the previous line
  }
  println(humanDescription)

  //decomposing sequences (unapply sequence)
  val vararg = numbers match {
    case List(1, _*) => "Starting with one"
  }

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList{
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }

  val myList:MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val decomposed = myList match {
    case MyList(1, 2, _*) => "starting with 1, 2"
    case _ => "something else"
  }

  println(decomposed)


  //Custom return types must implement:
  // isEmpty: Boolean, get: something
  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false
      override def get: String = person.name
    }
  }

  println(bob match {
    case PersonWrapper(n) => s"this person name is ${n}"
    case _ => "An alien"
  })
}
