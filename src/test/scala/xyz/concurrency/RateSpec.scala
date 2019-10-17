package xyz.concurrency

import org.scalatest._

class RateSpec extends FlatSpec with Matchers {

  val getRate: Double => Rate = (rate: Double) => Rate("EUR", "MYR", rate, 1L)
  val getRateByTimestamp: Long => Rate = (timestamp: Long) => Rate("EUR", "MYR", 1, timestamp)

  "A rate" should "not has a same from and to" in {
    an[IllegalArgumentException] should be thrownBy Rate("A", "A", 1, 1)
    noException should be thrownBy Rate("A", "B", 1, 1)
  }

  it should "has a rate of greater than zero" in {
    an[IllegalArgumentException] should be thrownBy Rate("A", "B", 0, 1)
    an[IllegalArgumentException] should be thrownBy Rate("A", "B", -1, 1)
  }

  "A rate list" should "be created with list of rate" in {
    val rates: List[Rate] = (1 to 100).map(n => Rate("MYR", "EUR", n, n)).toList
    val rateList: RateList = RateList(rates)

    rateList.rates.size should be(rates.size)
  }

  it should "has a lowest rate" in {
    val rates = RateList(List(2, 3, 2, 3, 1, 4).map(r => getRate(r)))
    rates.lowestRate should be(Some(getRate(1)))
  }

  it should "pick the first lowest rate" in {
    val rates = RateList(List(1, 3, 2, 3, 1, 4).map(r => getRate(r)))
    rates.lowestRate should be(Some(getRate(1)))
  }

  it should "be none when no rates" in {
    val rates = RateList(List())
    rates.lowestRate should be(None)
    rates.highestRate should be(None)
    rates.earliestRate should be(None)
    rates.latestRate should be(None)
  }

  it should "has a highest rate" in {
    val rates = RateList(List(2, 3, 2, 3, 1, 4).map(r => getRate(r)))
    rates.highestRate should be(Some(getRate(4)))
  }

  it should "pick the first highest rate" in {
    val rates = RateList(List(1, 4, 2, 3, 1, 4).map(r => getRate(r)))
    rates.highestRate should be(Some(getRate(4)))
  }

  it should "has a earliest timestamp" in {
    val rates = RateList(List(2, 3, 2, 3, 1, 4).map(t => getRateByTimestamp(t)))
    rates.earliestRate should be(Some(getRateByTimestamp(1)))
  }

  it should "pick the first earliest rate" in {
    val rates = RateList(List(2, 3, 1, 3, 1, 4).map(t => getRateByTimestamp(t)))
    rates.earliestRate should be(Some(getRateByTimestamp(1)))
  }

  it should "has a latest timestamp" in {
    val rates = RateList(List(2, 3, 2, 3, 1, 4).map(t => getRateByTimestamp(t)))
    rates.latestRate should be(Some(getRateByTimestamp(4)))
  }

  it should "pick the first latest rate" in {
    val rates = RateList(List(4, 3, 1, 3, 1, 4).map(t => getRateByTimestamp(t)))
    rates.latestRate should be(Some(getRateByTimestamp(4)))
  }
}
