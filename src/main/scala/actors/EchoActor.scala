package actors

import actors.EchoActor.{ChangeInitialState, MessageToEcho}
import akka.actor.SupervisorStrategy.{Decider, Restart, Resume, Stop}
import akka.actor.{Actor, ActorInitializationException, ActorKilledException, ActorLogging, ActorRef, DeathPactException, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import exceptions.{BoomException, NotSoDangerousException, NuclearExplosionException}

object EchoActor {
  def props() :Props = {
    Props(classOf[EchoActor])
  }
  case class MessageToEcho(text: String)
  case class ChangeInitialState(list: List[String])
}

class EchoActor extends Actor with ActorLogging {

  var initialSlaveState: List[String] = List.empty
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
    initialSlaveState = initialSlaveState :+ "initial"
    echoActorExecutorActorRef = context.actorOf(EchoActorSlave.props(initialSlaveState), "echoActorSlave")
    context.watch(echoActorExecutorActorRef)
  }

  override def supervisorStrategy : SupervisorStrategy = {
    OneForOneStrategy()(customDecider)
  }

  override def receive: Receive = {
    case ChangeInitialState(state) =>
      initialSlaveState = state
      log.info(s"Initial state for echoActorSlaves changed to $initialSlaveState")
    case message: MessageToEcho => echoActorExecutorActorRef ! message
    case Terminated(actorRef) =>
      log.info(s"My slave $actorRef is dead. Whatever man! I gonna buy a new one.")
      echoActorExecutorActorRef = context.actorOf(EchoActorSlave.props(initialSlaveState), "echoActorSlave")
      context.watch(echoActorExecutorActorRef)
  }
}
