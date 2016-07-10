# akka-supervision-toy
Proyect to play with Akka actor supervision

---

Execute ```sbt clean compile run``` to compile and execute this little toy.

Interact with the app writing in console stdin the following:

* Any message. 
* Plop
* Boom
* DoomsDay

The message written will be showed via actor log. The actor echoActor delegates the dangerous job to it echoActorSlave's.

If the message is Plop a ```NotSoDangerousException``` is thrown by the echoActorSlave Actor. The echoActor supervises this exception telling the slave to resume the job.

If the message is Boom a ```BoomException``` is thrown by the echoActorSlave Actor. The echoActor supervises this exception telling the slave to restart.

If the message is DoomsDay a  ```NuclearExplosionException ``` is thrown by the echoActorSlave Actor. The echoActor supervises this exception telling the slave to stop. Then echoActor creates a new slave.


type changeState: followed by comma separated strings, it will change the state of echoActor i.e changeState:the,change,is,real
