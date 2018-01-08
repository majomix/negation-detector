package fiit.nlp.NegatedKeywordsExtractor;

import java.util.Comparator;
import java.util.Map.Entry;

public class EntrySetComparator implements Comparator<Entry<String, Double>>
{
	@Override
	public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
		if(o1.getValue() < o2.getValue()) {
			return 1;
		} else if(Math.abs(o1.getValue() - o2.getValue()) < .000000001) {
			return 0;
		} else {
			return -1;
		}
	}	
}