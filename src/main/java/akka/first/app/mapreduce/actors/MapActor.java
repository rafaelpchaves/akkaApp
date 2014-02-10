package akka.first.app.mapreduce.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import akka.actor.UntypedActor;
import akka.first.app.mapreduce.messages.MapData;
import akka.first.app.mapreduce.messages.WordCount;

public class MapActor extends UntypedActor {

	String[] STOP_WORDS = { "a", "am", "an", "and", "are", "as", "at", "be",
			"do", "go", "if", "in", "is", "it", "of", "on", "the", "to" };
	List<String> STOP_WORDS_LIST = Arrays.asList(STOP_WORDS);

	public MapActor() {
		System.out.println("construindo map actor");
	}
	
	@Override
	public void onReceive(Object message) throws Exception {

		if (message instanceof String) {
			System.out.println("[map actor] processando mensagem");
			String work = (String) message;
			getSender().tell(evaluateExpression(work));
		} else {
			unhandled(message);
		}
	}

	
	private MapData evaluateExpression(String line) {
		List<WordCount> dataList = new ArrayList<WordCount>();
		StringTokenizer parser = new StringTokenizer(line);
		while (parser.hasMoreTokens()) {
			String word = parser.nextToken().toLowerCase();
			if (!STOP_WORDS_LIST.contains(word)) {
				dataList.add(new WordCount(word, Integer.valueOf(1)));
			}
		}
		return new MapData(dataList);
	}

}
