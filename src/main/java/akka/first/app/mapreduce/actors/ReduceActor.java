package akka.first.app.mapreduce.actors;

import java.util.HashMap;
import java.util.List;

import akka.actor.UntypedActor;
import akka.first.app.mapreduce.messages.MapData;
import akka.first.app.mapreduce.messages.ReduceData;
import akka.first.app.mapreduce.messages.WordCount;

public class ReduceActor extends UntypedActor {

	public ReduceActor() {
		System.out.println("construindo reduce actor");
	}
	
	@Override
	public void onReceive(Object message) throws Exception {

		if (message instanceof MapData) {
			System.out.println("[reduce actor] processando mensagem");
			MapData mapData = (MapData) message;
			getSender().tell(reduce(mapData.getDataList()));
		}
	}

	private ReduceData reduce(List<WordCount> dataList) {
		HashMap<String, Integer> reducedMap = new HashMap<String, Integer>();
		for (WordCount wordCount : dataList) {
			if (reducedMap.containsKey(wordCount.getWord())) {
				Integer value = (Integer) reducedMap.get(wordCount.getWord());
				value++;
				reducedMap.put(wordCount.getWord(), value);
			} else {
				reducedMap.put(wordCount.getWord(), Integer.valueOf(1));
			}
		}
		return new ReduceData(reducedMap);
	}

}
