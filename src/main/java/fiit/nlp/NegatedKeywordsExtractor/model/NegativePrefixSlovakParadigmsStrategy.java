package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.Set;

public class NegativePrefixSlovakParadigmsStrategy implements INegativePrefixStrategy {
	
	public void detect(AbstractAnnotatedWord wordEntry) {
		// String[] slova = new String[] { "disponibilný", "agrárny", "neandertálec", "disfunkcia", "agramatický", "nepovedať" };
		
		Set<ParadigmEntry> set = WordDictionaryLoaderParadigms.getInstance();
		
		String word = wordEntry.word.toLowerCase();
		String[] result = word.split("(?<=^(ne|a|i|de|dis|anti|kontra))");
		
		if(result.length == 1) {
			return;
		}
		
		ParadigmEntry checkedEntry = new ParadigmEntry(result[1], wordEntry.partOfSpeechTag);
		if(set.contains(checkedEntry)) {
			switch(wordEntry.partOfSpeechTag) {
			case "G":
			case "A":
				wordEntry.negator = "atr";
			case "V":
				wordEntry.negator = "pred";
			}
		}
	}
}
