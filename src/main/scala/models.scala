package com.tbrown.twitterStream
import com.github.nscala_time.time.Imports._
import spray.json.DefaultJsonProtocol
//import spray.httpx.SprayJsonSupport._
import spray.json._
import DefaultJsonProtocol._
import scala.util.matching.Regex
import twitter4j._

case object Report
case class TweetFound(status: Status)

case class Emoji(
  name:         String,
  unified:      String,
  variations:   List[String],
  docomo:       Option[String],
  au:           Option[String],
  softbank:     Option[String],
  google:       Option[String],
  image:        String,
  sheet_x:      Int,
  sheet_y:      Int,
  short_name:   String,
  short_names:  List[String],
  text:         Option[String],
  apple_img:    Boolean,
  hangouts_img: Boolean,
  twitter_img:  Boolean
) {
  override def toString: String =
    new String(unified.split("-").flatMap{ codepoint =>
      Character.toChars(Integer.parseInt(codepoint, 16))
    })
}

object Emoji extends DefaultJsonProtocol {
  implicit val EmojiFormat = jsonFormat16(Emoji.apply)
}

case class Tweet (
  retweeted: Boolean,
  id: Long,
  created_at: DateTime,
  favorrite_count: Int,
  text: String,
  source: String,
  retweet_count: Int,
  id_str: String,
  user: User
)

object Tweet extends DefaultJsonProtocol {
  implicit val TweetFormat = jsonFormat9(Tweet.apply)
}

case class User (
  location: String,
  statuses_count: Int,
  lang: String,
  id: Long,
  favourites_count: Int,
  description: String,
  name: String,
  created_at: DateTime,
  followers_count: Int,
  friends_count: Int,
  id_str: String,
  profile_image_url: String
)

object User extends DefaultJsonProtocol {
  implicit val UserFormat = jsonFormat12(User.apply)
}
