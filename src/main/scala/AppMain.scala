import actors.{EchoActor, ReadStdinActor}
import akka.actor.ActorSystem

object AppMain extends App{

  println("Hello stranger: \n" +
    "type Plop to generate a NotSoDangerousException in EchoActorSlave, It will continue the job it was doing.\n" +
    "type Boom to generate a BoomException in EchoActorSlave, It will restart the actor.\n" +
    "type DoomsDay to generate a NuclearExplosionException in EchoActorSlave, It will kill the actor.\n" +
    "type changeState: followed by comma separated strings, it will change the sate of echoActor\nand the slaves i.e changeState:the,change,is,real")

  val system = ActorSystem("akka-test-miscel")
  val echoActorRef = system.actorOf(EchoActor.props(), "echoActor")
  val readStdInActorRef = system.actorOf(ReadStdinActor.props(echoActorRef), "readStdinActor")
  readStdInActorRef ! ReadStdinActor.Start
}
