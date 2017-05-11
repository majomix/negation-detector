package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.Map;

public class NegativePrefixEnglishStrategy implements INegativePrefixStrategy {

	public String[] getPrefixes() {
		return new String[] { "un", "im", "in", "a" };
	}
	
	@Override
	public String detect(AbstractAnnotatedWord wordEntry) {
		Map<String, WordEntrySentiWordNet> sentiWordNet = WordDictionaryLoaderSentiWordNet.getInstance();
		WordEntrySentiWordNet checkedEntry = sentiWordNet.get(wordEntry.lemma.toLowerCase());
		
		if(checkedEntry != null && checkedEntry.isNegative()) {
			switch(checkedEntry.pos) {
			case "a":
				return "att";
			case "n":
				return "sbs";
			case "r":
				return "adv";
			case "v":
				return "pre";
			}
		}
		
		return "";
	}

}
