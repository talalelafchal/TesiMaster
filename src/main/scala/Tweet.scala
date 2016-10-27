import java.sql.Time

/**
  * Created by Talal on 27.10.16.
  */
case class Tweet(var time:Time,
                 var tweetId:Long,
                 var userId:Long,
                 var retweetId:Option[Long],
                 var inReplyToStatusId:Int,
                 var isTruncated:Boolean,
                 var mentionedUsers: Option[List[Long]],
                 var hashTags:String,
                 var text: Option[String] ) {

}
