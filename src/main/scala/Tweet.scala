import java.sql.Time

/**
  * Created by Talal on 27.10.16.
  */
trait Tweet {
  val tweetId: Long
}

case class CompleteTweet(time:Time,
                 tweetId:Long,
                 user:User,
                 retweetId:Option[Tweet],
                 inReplyToStatusId:Int,
                 isTruncated:Boolean,
                 mentionedUsers: List[User],
                 hashTags: List[String],
                 text: Option[String] ) extends Tweet {

}

case class AbsentTweet(tweetId: Long) extends Tweet
