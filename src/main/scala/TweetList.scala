import scala.collection.mutable

/**
  * Created by Talal on 24.11.16.
  */
class TweetList() {
  private val tweetHashMap = new mutable.HashMap[Long, Tweet]

  def apply(id: Long): Tweet = tweetHashMap.getOrElse(id,AbsentTweet(id))
  def getTweet(id: Long): Option[Tweet] = tweetHashMap.get(id)
  def addTweet(id: Long, tweet: Tweet) = tweetHashMap.put(id,tweet)
}
