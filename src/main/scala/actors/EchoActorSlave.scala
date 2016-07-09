package actors

import akka.actor.{Actor, ActorLogging, Props}
import exceptions.{BoomException, NotSoDangerousException, NuclearExplosionException}


object EchoActorSlave {
  def props(): Props = {
    Props(classOf[EchoActorSlave])
  }
}

class EchoActorSlave extends Actor with ActorLogging {

  override def postRestart(reason: Throwable): Unit = {
    super.postRestart(reason)
    log.info("I've been restarted, something went somewhat wrong :/")
  }

    override def receive: Receive = {
      case e @ EchoActor.MessageToEcho("Plop") => throw new NotSoDangerousException(message = Some(s"There was some explosions right here -> ${Some(e.toString)}, as usual, continuing my job, boys."))
      case e @ EchoActor.MessageToEcho("Boom") => throw new BoomException(message = Some(s"There was some explosions right here -> ${Some(e.toString)} need to restart"))
      case e @ EchoActor.MessageToEcho("DoomsDay") => throw new NuclearExplosionException(message = Some(s"THE END IS NEAR!!! -> ${Some(e.toString)} WE ARE DOOMED! DEATH!!!"))
      case EchoActor.MessageToEcho(message) => log.info(s"I've received the following text: $message")
  }
}
