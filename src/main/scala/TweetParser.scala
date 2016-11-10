/**
  * Created by Talal on 27.10.16.
  */

import java.sql.Time

import scala.io.Source

object TweetParser {


  def main(args: Array[String]): Unit = {
    // get file path
    val filename = args(0)

    // get lines
    val lines: List[String] = Source.fromFile(filename).getLines().toList

    // create a list of tweets
    val tweets: List[Tweet] = lines.map { line => lineToTweet(line)}

  }


  /** Parse tweets file and create a tweet object
    *
    * @param line
    * @return Tweet Object
    */
  def lineToTweet(line: String): Tweet = {
    val res = line.split('|')
    //time:Time,tweetId:Long,User:User,retweetId:Option[Long],inReplyToStatusId:Int,isTruncated:Boolean,mentionedUsers: List[Long],hashTags:String,text: Option[String]
    val date = Time.valueOf(res(0))
    val tweetId = res(1).toLong
    val userId = res(2).toLong
    val user = User.getUser(userId, tweetId)
    Tweet(date, tweetId, user, optionLong(res(3)), res(4).toInt,
      res(5).toBoolean, listString(res(6)), res(7), Some(res(8)))
  }

  /**
    * get mentionedUsers
    * @param string mentionedUser
    * @return List of mentionedUsers
    */
  def listString(string: String): List[Long] =
    if (string.isEmpty()) List()
    else string.split(",").toList.map(x => x.toLong)

  /**
    * get retweetedId
    * @param intString retweetedId
    * @return retweetId if exists
    */
  def optionLong(intString: String): Option[Long] = {
    if (intString.isEmpty) None
    else Some(intString.toLong)
  }

  /**
    * get a list of montionedUsers fo each tweet
    * @param list
    */
  def mentionedUsers(list: List[Tweet]) = {
    val l = list.filter(x => !x.mentionedUsers.isEmpty)
    l.foreach(x => println(x.mentionedUsers))
  }

}
