/**
  * Created by Talal on 27.10.16.
  */

import java.io.{BufferedWriter, File, FileWriter}
import java.sql.Time

import scala.io.Source
import scala.util.Random


object TweetParser extends App {

  // get file path
  val filename = args(0)

  // get line
  val line: List[String] = Source.fromFile(filename).getLines().toList

  val tweetList = new TweetList()

  // create a list of tweets
  val tweets: List[CompleteTweet] = line.map { line => lineToTweet(line) }

  createCsvFile()

  //    val retweetsOfAbsentTweets = tweets.filter { t => t match {
  //      case CompleteTweet(_,_,_,Some(t2),_,_,_,_,_) => t2.isInstanceOf[AbsentTweet]
  //      case _ => false
  //    }}
  //
  //    println(retweetsOfAbsentTweets.size)
  //
  //    println((retweetsOfAbsentTweets.map{ _.retweetId.get.tweetId } intersect tweets.map{_.tweetId}).size)


  /** Parse tweets file and create a tweet object
    * and a tweet list with existing and absent tweets
    *
    * @param line
    * @return Tweet Object
    */
  def lineToTweet(line: String): CompleteTweet = {
    val res = line.split("\\|", -1)
    //time:Time,tweetId:Long,User:User,retweetId:Option[Long],inReplyToStatusId:Int,isTruncated:Boolean,mentionedUsers: List[Long],hashTags:String,text: Option[String]
    val date = Time.valueOf(res(0))
    val tweetId = res(1).toLong
    val userId = res(2).toLong
    val retweetId: Option[Tweet] = optionLong(res(3)).map { id => tweetList(id) }
    val user = User.getUser(userId)
    val hashTags = res(7).split(",").toList
    val mentionedUsers = listString(res(6)).map { userId => User.getUser(userId) }
    val text = res.drop(8).reduce(_ + "|" + _)

    val result = CompleteTweet(date, tweetId, user, retweetId, res(4).toInt,
      res(5).toBoolean, mentionedUsers, hashTags, Some(text))

    tweetList.addTweet(tweetId, result)
    result
  }

  /**
    * get mentionedUsers
    *
    * @param string mentionedUser
    * @return List of mentionedUsers
    */
  def listString(string: String): List[Long] =
    if (string.isEmpty()) List()
    else string.split(",").toList.map(x => x.toLong)

  /**
    * get retweetedId
    *
    * @param intString retweetedId
    * @return retweetId if exists
    */
  def optionLong(intString: String): Option[Long] = {
    if (intString.isEmpty) None
    else Some(intString.toLong)
  }



  /**
    * creat csv files tweets/ if there is no tweetid  -1, if original tweet is absent 0, else tweetId
    */
  def createCsvFile() = {
    val tweetFile = new File("tweets.csv")
//    val retweetFile = new File("retweets.csv")

    val tweetbf = new BufferedWriter(new FileWriter(tweetFile))
    tweetbf.write("tweetId,retweetId,hour,minute,second\n")
    tweets.foreach(x => tweetbf.write(x.tweetId.toString +
      ',' + handleRetweet(x)+
      ',' + x.time.toString.split(":")(0) +
      ',' + x.time.toString.split(":")(1) +
      ',' + x.time.toString.split(":")(2) + '\n'))
    tweetbf.close()

  }

  def retweetIdtoFile(x: Tweet): String = x match {
    case _: AbsentTweet => "0"
    case _: CompleteTweet => x.tweetId.toString
  }

  def handleRetweet(x: CompleteTweet) ={
    if(x.retweetId!= None){
      retweetIdtoFile(x.retweetId.get)
    }
    else "-1"
  }

}
