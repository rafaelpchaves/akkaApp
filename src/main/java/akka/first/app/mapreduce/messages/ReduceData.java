package akka.first.app.mapreduce.messages;

import java.util.HashMap;

public class ReduceData {

	
	private final HashMap<String, Integer> reduceDataList;

	public ReduceData(HashMap<String, Integer> reduceDataList) {
		this.reduceDataList = reduceDataList;
	}

	public HashMap<String, Integer> getReduceDataList() {
		return reduceDataList;
	}

	
	


}
