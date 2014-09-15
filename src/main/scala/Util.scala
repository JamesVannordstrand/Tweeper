package com.tbrown.twitterStream
import twitter4j._
import akka.event.EventStream
import scala.util.{Success, Failure}
import twitter4j.TwitterObjectFactory

case class TweetJson(json: String)

object Util {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("F0qSJRDC0wXhQ6FXeU3pZ5xJm")
    .setOAuthConsumerSecret("lx7zmWpOQ6aCc7k4P05IeDrjBmQSXhpApvbKogLr7voMHXkYNz")
    .setOAuthAccessToken("16603768-mYPj5hsiTAd2NJNAKOUrlb2eiA5w9povP0wqCzJS7")
    .setOAuthAccessTokenSecret("mVgKca5yTMALMYDbzxPsXSpM495f55ZW5WcSjTcv7SeZw")
    .setJSONStoreEnabled(true)
    .build


  def simpleStatusListener(eventStream: EventStream) = new StatusListener() {
    def onStatus(status: Status) {
      val tweetJson = TweetJson(TwitterObjectFactory.getRawJSON(status))
      eventStream.publish(tweetJson)
    }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(stallWarning: StallWarning) {}
  }
}
