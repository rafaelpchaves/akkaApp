package akka.first.app.mapreduce.messages;

import java.util.List;

public class MapData {

	private final List<WordCount> dataList;

	public List<WordCount> getDataList() {
		return dataList;
	}
	
	public MapData(List<WordCount> dataList){
		this.dataList = dataList;
	}
	
}
