/**
  * Created by Talal on 27.10.16.
  */

import java.io.{BufferedWriter, File, FileWriter}
import java.sql.Time

import scala.io.Source
import co.theasi.plotly._

import scala.util.Random


object TweetParser extends App {

  // get file path
  val filename = args(0)

  // get line
  val line: List[String] = Source.fromFile(filename).getLines().toList

  val tweetList = new TweetList()

  // create a list of tweets
  val tweets: List[CompleteTweet] = line.map { line => lineToTweet(line) }

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


  // Generate uniformly distributed x
  val xs =tweets.map(x=> x.time.toString)

  // Generate random y
  val ys = tweets.map(_=>getRandom())

  def getRandom():Double= {
    Random.nextDouble()*6
  }

//  val p = Plot().withScatter(xs,ys,ScatterOptions().mode(ScatterMode.Marker))
//
//  draw(p, "twitter", writer.FileOptions(overwrite=true))
  // returns  PlotFile(pbugnion:173,basic-scatter)

  val file = new File("tweets.csv")
  val bw = new BufferedWriter(new FileWriter(file))
  bw.write("time,random\n")
  tweets.foreach(x=> bw.write(x.time.toString +','+ getRandom().toString+'\n'))
  bw.close()

}
