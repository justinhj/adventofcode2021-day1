package org.justinhj

import scalaz._
import Scalaz._
import scala.io.Source

object Adventofcode2021day1 {

  // In order to run Scalaz law checks we need Equals
  // Note that the left and right fields naturally change during append so we only
  // consider the count when comparing two IncreaseCount's
  implicit val equalIncreaseCount: Equal[IncreaseCount] = new Equal[IncreaseCount] {
    def equal(a1: IncreaseCount, a2: IncreaseCount): Boolean = {
      a1.count == a2.count
    }
  }

  // Monoid instance that does all the work...
  implicit val increaseCountInstance : Monoid[IncreaseCount] = {
    Monoid.instance[IncreaseCount]({
      case (l: IncreaseCount, r: IncreaseCount) =>
        val inc = if(l.right < r.left) 1 else 0
        IncreaseCount(l.count + r.count + inc, l.left, r.right)
    },
      IncreaseCount(0,Int.MinValue,Int.MaxValue))
  }

  case class IncreaseCount(count: Int, left: Int, right: Int)

  // A function doing all the work
  def countIncreases(in: List[Int]): Int = {
    Traverse[List].traverse(in) {
      n =>
        Const[IncreaseCount,Any](IncreaseCount(0,n,n))
    }.getConst.count
  }

  def main(args: Array[String]): Unit = {

    val exampleInput = List(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

    val exampleInputIncreasedCount = exampleInput.map(n => IncreaseCount(0,n,n))

    val folded = Foldable[List].fold(exampleInputIncreasedCount)
    println(s"Part 1 example using fold. Count is ${folded.count} expected 7")

    // Part 1 input
    val part1Input = Source.fromResource("org/justinhj/adventday1input1.txt")
      .mkString
      .split("\n")
      .toList.map(_.toInt)

    val part1InputIncreasedCount = part1Input.map(n => IncreaseCount(0,n,n))

    val part1Folded = Foldable[List].fold(part1InputIncreasedCount)
    println(s"Part 1. Count is ${part1Folded.count}")

    // Of course you can do with Traverse

    val part1Traversed = Traverse[List].traverse(part1Input) {
      n =>
        Const[IncreaseCount,Any](IncreaseCount(0,n,n))
    }
    println(s"Part 1. Traverse says count is ${part1Traversed.getConst.count}")

    // Step 2

    val exampleZipped = exampleInput.zip(exampleInput.tail.zip(exampleInput.tail.tail))
    val exampleSums = exampleZipped.map {
      case (a,(b,c)) => a + b + c
    }

    val part2ExampleTraversed = Traverse[List].traverse(exampleSums) {
      n =>
        Const[IncreaseCount,Any](IncreaseCount(0,n,n))
    }
    println(s"Part 2 example. Traverse says count is ${part2ExampleTraversed.getConst.count}")

    val part2Input = part1Input.zip(part1Input.tail.zip(part1Input.tail.tail))

    val part2Sums = part2Input.map {
      case (a,(b,c)) => a + b + c
    }

    val part2Traversed = Traverse[List].traverse(part2Sums) {
      n =>
        Const[IncreaseCount,Any](IncreaseCount(0,n,n))
    }
    println(s"Part 2. Traverse says count is ${part2Traversed.getConst.count}")

  }
}
