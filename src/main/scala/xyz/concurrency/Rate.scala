package xyz.concurrency

import play.api.libs.json._

final case class Rate(from: String, to: String, rate: Double, timestamp: Long)

object Rate {
  implicit val implicitWrites: Writes[Rate] = (rate: Rate) => {
    Json.obj("from" -> rate.from, "to" -> rate.to, "rate" -> rate.rate, "timestamp" -> rate.timestamp)
  }
}

final case class RateList(
    rates: List[Rate],
    earliestRate: Option[Rate],
    latestRate: Option[Rate],
    lowestRate: Option[Rate],
    highestRate: Option[Rate])

object RateList {
  implicit val implicitWrites: Writes[RateList] = (rates: RateList) => {
    Json.obj(
      "rates" -> rates.rates,
      "earliestRate" -> rates.earliestRate,
      "latestRate" -> rates.latestRate,
      "lowestRate" -> rates.lowestRate,
      "highestRate" -> rates.highestRate)
  }

  def apply(rates: List[Rate]): RateList = {
    RateList(rates, Some(rates.head), Some(rates.head), Some(rates.head), Some(rates.head))
  }
}
