package com.tutorials.scala.functional

import scala.util.Try

/**
 * Created by Alex
 */
object SugarSyntax extends App {
  //  #1: Methods with single parameter: The parameter can be provided inside the parenthesis or as an expression.
  def singleArgMethod(arg: Int): String = s"$arg little ducks"

  //  val description = singleArgMethod(43) // or:
  val desciption = singleArgMethod {
    //Complex code
    43
  }

  println(desciption)

  //This syntax helps to write Try block similar to the Java version:
  val aTryInstance = Try {
    throw new RuntimeException
  }

  List(1, 2, 3).map({
    _ + 1
  }) // this is from:  x => x + 1


  //  #2: Single Abstract Method: Classes or Traits with a single abstract method can be implemented as a lambda
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = _ + 1 //equivalent to (x: Int) => x + 1. This implements the "act" method

  abstract class AnAbstractType {
    def implemented: Int = 33

    def notImplemented(a: Int): Int
  }

  val anAbstractInstance: AnAbstractType = (a: Int) => a + 33

  println(anInstance.act(1))
  println(anAbstractInstance.notImplemented(1))


  // #3: The :: and #:: methods are special: methods that en in colon (:) are right-associative (evaluated the right-side of the expression first).
  val aPrependedList = 2 :: List(3, 4) // List(3,4).::(2) (adds and element at the beginning of the list)
  println(aPrependedList)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int] //new MyStream[Int].-->:3.-->:2.-->:1

  //#4: multi-word method naming
  class TeenGirl(name: String) {
    def `and then said`(gossip: String): Unit = println(s"$name and then said $gossip")
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet!!"

  //  #5: Infix types
  class Composite[A, B]

  //  val composite: Composite[Int, String] = ???
    val composite: Int Composite String = ???

  class -->[A, B]

  val arrow: Int --> String = ???


  //  #6: The update() method: Is special, much like apply()
  val anArray = Array(1, 2, 3)
  anArray(2) = 7 //anArray.update(2, 7)

  //  #7: Setters for mutable containers: The compiler calls the setter method automatically
  class Mutable {
    private var internalMember: Int = 0

    def member: Int = internalMember // "getter"

    def member_=(value: Int): Unit = internalMember = value //"setter"
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // aMutableContainer.member_=(42)
}
