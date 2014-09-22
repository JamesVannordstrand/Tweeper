package com.tbrown.twitterStream

import akka.actor._
import akka.actor.{ActorContext, Actor}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import spray.can.Http
import spray.can.Http.RegisterChunkHandler
import spray.can.server.Stats
import spray.http._
import spray.httpx.SprayJsonSupport._
import spray.httpx.marshalling.BasicMarshallers
import spray.routing._
import spray.util._
import HttpMethods._
import MediaTypes._
import MediaTypes._

class ServiceActor extends Actor with ActorLogging with TweetStatService with ContentService {
  def actorRefFactory = context

  val route =
    contentRoute ~
    statsRoute

  def receive = runRoute(route)
}

trait TweetStatService extends HttpService {

  val statsRoute = {
    path("hello") {
      getFromResource("index.html")
    } ~
    path("ping") {
      complete("PONG!")
    } ~
    path("stats") {
      complete(TweetMetrics.stats)
    } ~
    path("stream" / "filter") {
      ctx => {
        val peer = ctx.responder
        actorRefFactory actorOf Props(new Streamer[FilterTweetJson](peer))
      }
    }
  }
}

trait ContentService extends HttpService {
  val contentRoute = {
    path("index") { getFromResource("index.html") } ~
    pathPrefix("config.js") { get { getFromResource("config.js") } } ~
    pathPrefix("vendor") { get { getFromResourceDirectory("vendor") } } ~
    pathPrefix("css") { get { getFromResourceDirectory("css") } } ~
    pathPrefix("app") { get { getFromResourceDirectory("app") } } ~
    pathPrefix("img") { get { getFromResourceDirectory("img") } } ~
    pathPrefix("js") { get { getFromResourceDirectory("js") } } ~
    pathPrefix("font") { get { getFromResourceDirectory("font") } }
  }
}
