package chapter4{
  package test1{
    package test11{

    }
  }
}

import scala.collection.mutable
import java.util.{HashMap => _}
/**
  * @author: wq
  * @date: 2018/7/25 14:08
  */
object YingShe {
  def main(args: Array[String]): Unit = {
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
    val scores1 = mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
    val scores2 = new mutable.HashMap[String, Int]
    val bobsScore = scores1("Bob")
    val aliceScore = if (scores1.contains("Alice")) scores1("Alice") else 0
    val cindyScore = scores1.getOrElse("Cindy", 0)

    var score1 = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
    score1 = score1 + ("Fred" -> 7)
    println(score1.keySet)
  }
}

/**
* App特质扩展自另一个特质DelayedInit
*/
object Hello extends App{
  if (args.length > 0) println("Hello " + args(0))
  else println("Hello World!")
}

object TrafficLightColor extends Enumeration{

  val Red, Yellow, Green = Value
}

class Person(name: String, age: Int){
  override def toString: String = super.toString
}

class Student(name: String, age: Int, salary: Double)
  extends Person(name, age) {

}

abstract class Per{
  val id: Int
  var name: String
}
