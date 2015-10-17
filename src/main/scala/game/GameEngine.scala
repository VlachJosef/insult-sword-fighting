package game

import model._

class GameEngine {
  val repo = new Repo
  def randomInt = scala.util.Random.nextInt(repo.entries.size)
  def randomInsults(howMany: Int) = repo.entries.map(entry => Insult(entry.id, entry.generalInsult)).take(howMany) // make it actually random
  def getComebackForInsult(insult: Insult) = repo.entries.filter( _.id == insult.id).map(entry => Comeback(insult.id,entry.comeback))

  def generatePlayer(howMany: Int) = {
    val insults = randomInsults(howMany)
    new Player(insults, insults.flatMap(getComebackForInsult))
  }

  def generatePirate = generatePlayer(3)

  /**
   * To improve code and to get rid of evil var variables we can use so called State monad.
   *
   * Concept of State Monad is similar to concept of Reader and Writer monad. For getting familiar with all these concepts
   * and to learn to recognize when they can be applied there exists this great presentation by Mark Hibberd:
   * https://www.youtube.com/watch?v=4KvRUBv7-Us
   *
   * Slide for this presentation are here:
   * http://mth.io/slides/patterns-in-types-ylj
   *
   * This talk was accompanied by workshop which source code is available here:
   * https://github.com/markhibberd/lambdajam-patterns-in-types
   *
   * It is highly recommended to go through this workshop and try to implement all challenges
   *
   * For completeness sake here is link to Mark Hibberd's website with description of his talk mentioned above:
   * http://mth.io/talks/patterns-in-types-ylj
   */
  var player: Player = generatePlayer(howMany = 2)
  var pirate: Player = _

  def fight() = {
    pirate = generatePirate
    println(pirate.knownInsults.map(_.value).take(1))
    println("Select your comeback")
    player.knownComebacks.foreach { c =>
      println(s"[${c.id}]    ${c.value}")
    }
  }
}

