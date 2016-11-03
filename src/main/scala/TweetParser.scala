/**
  * Created by Talal on 27.10.16.
  */

import java.sql.Time
import scala.io.Source

object TweetParser {


  def lineToTweet(line: String): Tweet = {
    val res = line.split('|')
    val date = Time.valueOf(res(0))
    //time:Time,tweetId:Long,userId:Long,retweetId:Option[Long],inReplyToStatusId:Int,isTruncated:Boolean,mentionedUsers: List[Long],hashTags:String,text: Option[String]
    Tweet(date,res(1).toLong,res(2).toLong,optionLong(res(3)),res(4).toInt,
      res(5).toBoolean,listString(res(6)),res(7),Some(res(8)))
  }

  def listString(string: String): List[Long] =
    if (string.isEmpty()) List()
    else string.split(",").toList.map(x=>x.toLong)

  def optionLong(intString:String) : Option[Long] ={
    if(intString.isEmpty) None
    else Some(intString.toLong)
  }

  def optionString(string: String):Option[String] ={
    if(string.isEmpty)None
    else Some(string)
  }

  def main(args: Array[String]): Unit = {
//    for (line <- Source.fromFile(filename).getLines()) {
//      val res = line.split('|')
//      val date = Time.valueOf(res(0))
//      var tweet = new Tweet(date,res(1).toInt,res(2).toInt,res(3).toInt,res(4).toInt,res(5).toBoolean,res(6).toInt,res(7),res(8))
//      tweetList+=tweet
//    }
//    println("List length : " + tweetList.size )
    var filename = args(0)
    val lines: List[String] = Source.fromFile(filename).getLines().toList
    val tweets: List[Tweet] = lines.map { line => lineToTweet(line)}

    //another

    //tweets.foreach { println }
    println(tweets.size + " tweets")
    mentionedUsers(tweets)
  }

  def mentionedUsers(list: List[Tweet])={
    val l = list.filter(x => ! x.mentionedUsers.isEmpty)
    l.foreach(x=> println(x.mentionedUsers))
  }
}
