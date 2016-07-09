package actors

import actors.ReadStdinActor.Start
import akka.actor.{Actor, ActorLogging, ActorRef, Props}

object ReadStdinActor {
  def props(echoActor: ActorRef): Props = Props(classOf[ReadStdinActor], echoActor)
  case object Start
}

class ReadStdinActor (echoDest: ActorRef)extends Actor with ActorLogging {
  override def receive: Receive = {
    case Start =>
      for (ln <- io.Source.stdin.getLines) {
        echoDest ! EchoActor.MessageToEcho(ln)
      }
  }
}
