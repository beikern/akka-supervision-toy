package actors

import actors.ReadStdinActor.Start
import akka.actor.{Actor, ActorLogging, ActorRef, Props}

import scala.util.matching.Regex

object ReadStdinActor {
  def props(echoActor: ActorRef): Props = Props(classOf[ReadStdinActor], echoActor)
  case object Start
}

class ReadStdinActor (echoDest: ActorRef)extends Actor with ActorLogging {

  val pattern = new scala.util.matching.Regex("(changeState:)(.*)", "title", "state")

  override def receive: Receive = {
    case Start =>
      for (ln <- io.Source.stdin.getLines) {
        val possibleState = for (m <- pattern.findFirstMatchIn(ln)) yield m group "state"
        for {
          state <- possibleState
        } yield {
          echoDest ! EchoActor.ChangeInitialState(state.split(",").toList)
        }
        possibleState match {
          case None =>
            echoDest ! EchoActor.MessageToEcho(ln)
          case _ =>
        }

      }
  }
}
