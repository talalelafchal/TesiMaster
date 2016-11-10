import scala.collection.mutable.{HashMap}


/**
  * Created by Talal on 10.11.16.
  */
case class User(userId: Long, tweetIdList: List[Long]) {

  def addTweet(id: Long) :User= {
    User(userId, tweetIdList :+ id)
  }
}

object User {

  private val usersHashMap = new HashMap[Long, User]

  def getUser(userId: Long, tweetId: Long): User = {
    if (!usersHashMap.contains(userId)) {
      usersHashMap += userId -> User(userId, List(tweetId))
    }
    else {
      usersHashMap += userId -> (usersHashMap(userId).addTweet(tweetId))
    }
    usersHashMap(userId)
  }

  def getUsersHashMap() = usersHashMap


}