package actors

import actors.EchoActor.MessageToEcho
import akka.actor.SupervisorStrategy.{Decider, Restart, Resume, Stop}
import akka.actor.{Actor, ActorInitializationException, ActorKilledException, ActorLogging, ActorRef, DeathPactException, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import exceptions.{BoomException, NotSoDangerousException, NuclearExplosionException}

object EchoActor {
  def props() :Props = {
    Props(classOf[EchoActor])
  }
  case class MessageToEcho(text: String)
}

class EchoActor extends Actor with ActorLogging {

  var echoActorExecutorActorRef: ActorRef = _

  val customDecider: Decider = {
    case _: ActorInitializationException    => Stop
    case _: ActorKilledException            => Stop
    case _: DeathPactException              => Stop
    case _: NuclearExplosionException       => Stop
    case _: BoomException                   => Restart
    case _: NotSoDangerousException         => Resume
    case _: Exception                       => Restart
  }

  override def preStart: Unit = {
    echoActorExecutorActorRef = context.actorOf(EchoActorSlave.props("initial"), "echoActorSlave")
    context.watch(echoActorExecutorActorRef)
  }

  override def supervisorStrategy : SupervisorStrategy = {
    OneForOneStrategy()(customDecider)
  }

  override def receive: Receive = {
    case message: MessageToEcho => echoActorExecutorActorRef ! message
    case Terminated(actorRef) =>
      log.info(s"My slave $actorRef is dead. Whatever man! I gonna buy a new one.")
      echoActorExecutorActorRef = context.actorOf(EchoActorSlave.props("initial"), "echoActorSlave")
      context.watch(echoActorExecutorActorRef)
  }
}
