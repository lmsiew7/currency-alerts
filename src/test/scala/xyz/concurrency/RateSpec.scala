package xyz.concurrency

import org.scalatest._

class RateSpec extends FlatSpec with Matchers {

  "A rate list" should "be created with list of rate" in {
    val rates: List[Rate] = (1 to 100).map(n => Rate("MYR", "EUR", n, n)).toList
    val rateList: RateList = RateList(rates)

    rateList.rates.size should be(rates.size)
  }

  it should "has a lower rate" in {
    val rate = Rate("EUR", "MYR", 1, 1L)
    val rateList = RateList(List(rate))

    rateList.lowestRate should be(Some(rate))
  }
}
