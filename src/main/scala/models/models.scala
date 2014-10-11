package com.tbrown.twitterStream
import org.joda.time.DateTime
import spray.httpx.SprayJsonSupport._
import spray.json._
import DefaultJsonProtocol._
import DateTimeJsonProtocol._

case object Report
case object ReportMetrics
case object TrackTweet
case object PublishStats
case class RouteTweet(json: String)
case class FilterStreamTweet(tweet: Tweet, hashtags: Hashtags, emojis: Emojis)
case class SampleStreamTweet(tweet: Tweet, hashtags: Hashtags, emojis: Emojis)
case class Hashtags(value: List[String])
case class Emojis(value: List[Emoji])
//case class TopElements[A: JsonWriter](value: List[A])

case class Hashtag(hashtag: String)
object Hashtag extends DefaultJsonProtocol {
  implicit val formats = jsonFormat1(Hashtag.apply)
}

case class StreamStats(avg: Double, count: Long)

object StreamStats extends DefaultJsonProtocol {
  implicit val formats = jsonFormat2(StreamStats.apply)
}
