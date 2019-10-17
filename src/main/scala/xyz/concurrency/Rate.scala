package xyz.concurrency

import play.api.libs.json._

final case class Rate(from: String, to: String, rate: Double, timestamp: Long) {
  require(from != to, "`from` and `to` cannot be the same value.")
  require(rate > 0, "`rate` must greater than 0.")
}

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
    val earliestRate = rates.map(_.timestamp) match {
      case rs if rs.isEmpty => None
      case rs               => rates.find(_.timestamp == rs.min)
    }

    val latestRate = rates.map(_.timestamp) match {
      case rs if rs.isEmpty => None
      case rs               => rates.find(_.timestamp == rs.max)
    }

    val lowestRate = rates.map(_.rate) match {
      case rs if rs.isEmpty => None
      case rs               => rates.find(_.rate == rs.min)
    }

    val highestRate = rates.map(_.rate) match {
      case rs if rs.isEmpty => None
      case rs               => rates.find(_.rate == rs.max)
    }

    RateList(rates, earliestRate, latestRate, lowestRate, highestRate)
  }
}
