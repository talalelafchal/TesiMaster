import scala.collection.mutable.{HashMap}


/**
  * Created by Talal on 10.11.16.
  */
case class User(userId: Long) {

}

object User {

  private val usersHashMap = new HashMap[Long, User]

  /**
    *
    * @param userId
    * If the user exists add the tweetId to the tweet list
    * else create a new User and add it to usersHashMap
    * @return
    */
  def getUser(userId: Long): User = usersHashMap.getOrElseUpdate(userId,User(userId))


  def getUsersHashMap() = usersHashMap


}