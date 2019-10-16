package xyz.concurrency

import collection.mutable.Stack
import org.scalatest._

class TrendSpec extends FlatSpec with Matchers {

  "A Trend" should "be able to" in {
    val result = new Trend().hey()
    result should be("lo")
  }

}