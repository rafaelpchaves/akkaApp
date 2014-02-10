package akka.first.app.mapreduce.actors;

import java.util.HashMap;
import java.util.Map;

import akka.actor.UntypedActor;
import akka.first.app.mapreduce.messages.ReduceData;
import akka.first.app.mapreduce.messages.Result;

public class AggregateActor extends UntypedActor {

	private Map<String, Integer> finalReducedMap = new HashMap<String, Integer>();

	
	public AggregateActor() {
		System.out.println("construindo agregate actor");
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof ReduceData) {
			System.out.println("processando reduce message");
			ReduceData reduceData = (ReduceData) message;
			aggregateInMemoryReduce(reduceData.getReduceDataList());
		} else if (message instanceof Result) {
			System.out.println("processando reduce");
			getSender().tell(finalReducedMap.toString());
		} else
			unhandled(message);
	}

	private void aggregateInMemoryReduce(Map<String, Integer> reducedList) {
		Integer count = null;
		for (String key : reducedList.keySet()) {
			if (finalReducedMap.containsKey(key)) {
				count = reducedList.get(key) + finalReducedMap.get(key);
				finalReducedMap.put(key, count);
			} else {
				finalReducedMap.put(key, reducedList.get(key));
			}
		}
	}
}
