package org.justinhj

import scalaz._
import Scalaz._
import org.justinhj.Adventofcode2021day1._
import org.scalacheck.{Properties, Test}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalacheck.util.ConsoleReporter
import scalaz.scalacheck.ScalazProperties._
import org.scalacheck.ScalacheckShapeless._

class IncreaseCountLaws extends AnyFlatSpec {

  "IncreasedCount" should "obey the monoid laws" in {

    val ml: Properties = monoid.laws[IncreaseCount]

    val testParams = Test.Parameters.default.withTestCallback(ConsoleReporter(1))

    val res = Test.checkProperties(testParams, ml)
    val passedCount = res.count(_._2.passed)

    assert(passedCount == res.size)
  }

  it should "obey left identity" in {
    val sample = IncreaseCount(-953793985,0,1)
    val result = sample |+| Monoid[IncreaseCount].zero
    assert(Equal[IncreaseCount].equal(result, sample))
  }

  it should "obey right identity" in {
    val sample = IncreaseCount(-953793985,0,1)
    val result = Monoid[IncreaseCount].zero |+| sample
    assert(Equal[IncreaseCount].equal(result, sample))
  }

  it should "obey associativity" in {
    val a = IncreaseCount(0,1,1)
    val b = IncreaseCount(0,2,2)
    val c = IncreaseCount(0,3,3)

    val r1 = a |+| (b |+| c)
    val r2 = (a |+| b) |+| c
    assert(Equal[IncreaseCount].equal(r1, r2))
  }

  it should "count increases in increasing list" in {
    val a = List(1,2,3)
    assert(countIncreases(a) == 2)
  }

  it should "count increases in decreasing list" in {
    val a = List(3,2,1)
    assert(countIncreases(a) == 0)
  }

  it should "count increases in increasing decreasing list" in {
    val a = List(1,2,3,2,1)
    assert(countIncreases(a) == 2)
  }
}