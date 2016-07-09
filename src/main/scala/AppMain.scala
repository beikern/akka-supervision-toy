import actors.{EchoActor, ReadStdinActor}
import akka.actor.ActorSystem

object AppMain extends App{


  implicit val system = ActorSystem("akka-test-miscel")
  val echoActorRef = system.actorOf(EchoActor.props(), "echoActor")
  val readStdInActorRef = system.actorOf(ReadStdinActor.props(echoActorRef), "readStdinActor")
  readStdInActorRef ! ReadStdinActor.Start
}
