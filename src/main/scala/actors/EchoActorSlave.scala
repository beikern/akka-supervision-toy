package actors

import akka.actor.{Actor, ActorLogging, Props}
import exceptions.{BoomException, NotSoDangerousException, NuclearExplosionException}


object EchoActorSlave {
  def props(initialState: List[String]): Props = {
    Props(classOf[EchoActorSlave], initialState)
  }
}

class EchoActorSlave (initialState: List[String]) extends Actor with ActorLogging {

  var phrasesTreated: List[String] = initialState
  phrasesTreated.toString()
  override def postRestart(reason: Throwable): Unit = {
    super.postRestart(reason)
    log.info(s"I've been restarted, something went somewhat wrong :/ my state is $phrasesTreated")
  }

    override def receive: Receive = {
      case e @ EchoActor.MessageToEcho("Plop") => throw new NotSoDangerousException(message = Some(s"There was some explosions right here -> ${Some(e.toString)}, as usual, continuing my job, boys."))
      case e @ EchoActor.MessageToEcho("Boom") => throw new BoomException(message = Some(s"There was some explosions right here -> ${Some(e.toString)} need to restart"))
      case e @ EchoActor.MessageToEcho("DoomsDay") => throw new NuclearExplosionException(message = Some(s"THE END IS COMING!!! -> ${Some(e.toString)} WE ARE DOOMED! DEATH!!!"))
      case EchoActor.MessageToEcho(message) =>
        phrasesTreated = phrasesTreated :+ message
        log.info(s"I've received the following text: $message and my state is $phrasesTreated")
  }
}
