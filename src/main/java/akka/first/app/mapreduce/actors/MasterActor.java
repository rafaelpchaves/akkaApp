package akka.first.app.mapreduce.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.first.app.mapreduce.messages.MapData;
import akka.first.app.mapreduce.messages.ReduceData;
import akka.first.app.mapreduce.messages.Result;
import akka.routing.RoundRobinRouter;

public class MasterActor extends UntypedActor {

	ActorRef mapActor = getContext().actorOf(new Props(MapActor.class).withRouter(new RoundRobinRouter(5)),"map");
	ActorRef reduceActor = getContext().actorOf(new Props(ReduceActor.class).withRouter(new RoundRobinRouter(5)),"reduce");
	ActorRef aggregateActor = getContext().actorOf(new Props(AggregateActor.class).withRouter(new RoundRobinRouter(5)), "aggregate");

	@Override
	public void onReceive(Object message) throws Exception {

		if(message instanceof String){
			mapActor.tell(message,getSelf());
		}else if(message instanceof MapData){
			reduceActor.tell(message, getSelf());
		}else if(message instanceof ReduceData){
			aggregateActor.tell(message);
		}else if(message instanceof Result){
			aggregateActor.forward(message, getContext());;
		}else
			unhandled(message);
	}

}
