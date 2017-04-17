package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.Set;

public class NegativePrefixSlovakParadigmsStrategy implements INegativePrefixStrategy {
	
	public String detect(AbstractAnnotatedWord wordEntry) {
		// String[] slova = new String[] { "disponibilný", "agrárny", "neandertálec", "disfunkcia", "agramatický", "nepovedať" };
		
		Set<ParadigmEntry> set = WordDictionaryLoaderParadigms.getInstance();
		
		String word = wordEntry.lemma.toLowerCase();
		String[] result = word.split("(?<=^(ne|a|i|de|dis|anti|kontra))");
		
		if(result.length == 1) {
			return "";
		}
		
		String lemma = result[1];
		
		// paradigms list feminimum and neutrum lemmas as masculinum
		if(lemma.endsWith("á") || lemma.endsWith("é")) {
			lemma = lemma.substring(0, lemma.length() - 1) + "ý";
		}
		
		ParadigmEntry checkedEntry = new ParadigmEntry(result[1], wordEntry.partOfSpeechTag);
		if(set.contains(checkedEntry)) {
			switch(wordEntry.partOfSpeechTag) {
			case "G":
			case "A":
				return "atr";
			case "V":
				return "pre";
			}
		}
		
		return "";
	}
}
