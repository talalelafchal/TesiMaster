import java.sql.Time

/**
  * Created by Talal on 27.10.16.
  */
case class Tweet(time:Time,
                 tweetId:Long,
                 user:User,
                 retweetId:Option[Long],
                 inReplyToStatusId:Int,
                 isTruncated:Boolean,
                 mentionedUsers: List[Long],
                 hashTags:String,
                 text: Option[String] ) {

}
