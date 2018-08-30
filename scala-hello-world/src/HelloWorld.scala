import scala.collection.mutable.ArrayBuffer
import scala.util.Sorting.quickSort

/**
  * @author: wq
  * @date: 2018/7/23 22:33
  */
object HelloWorld {
  def main(args: Array[String]): Unit = {
    val nums = new Array[Int](10)
    val a = new Array[String](10)
    val s = ArrayBuffer("Hello", "World123")
    val s1 = s.max
    quickSort(nums);
    println()
  }

  def testOne(b: String = "") = {
    "aaa";
  }

  def sum(args: Int*): Unit ={
    var result = 0
    for (arg <- args) result += arg
    result
  }

  def recursiveSum(args: Int*): Int={
    if (args.length == 0) 0
    else args.head + recursiveSum(args.tail: _*);
  }
}

class Person1{
  private var privateAge = 0
  def age = privateAge
  def age_=(newValue: Int): Unit ={

  }
}
