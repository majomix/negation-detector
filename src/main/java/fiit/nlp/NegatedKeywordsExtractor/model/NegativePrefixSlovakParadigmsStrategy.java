package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.ArrayList;
import java.util.Set;

public class NegativePrefixSlovakParadigmsStrategy implements INegativePrefixStrategy {
	
	public String[] getPrefixes() {
		return new String[] { "a", "ab", "an", "anti", "bez", "de", "dez", "dis", "dys", "i", "im", "kontra", "ne", "proti", "mimo" };
	}
	
	public String detect(AbstractAnnotatedWord wordEntry) {
		// String[] slova = new String[] { "disponibilný", "agrárny", "neandertálec", "disfunkcia", "agramatický", "nepovedať" };
		
		String[] prefixes = getPrefixes();
		
		Set<ParadigmEntry> set = WordDictionaryLoaderParadigms.getInstance();
		
		String word = wordEntry.lemma.toLowerCase();
		
		for(String prefix : prefixes) {
			String[] result = word.split("(?<=^" + prefix + ")");
			
			if(result.length > 1) {
				String lemma = result[1];
				if(lemma.length() < 2) {
					continue;
				}
				
				// paradigms list feminimum and neutrum lemmas as masculinum
				if(lemma.endsWith("á") || lemma.endsWith("é")) {
					lemma = lemma.substring(0, lemma.length() - 1) + "ý";
				}

				ArrayList<ParadigmEntry> entries = new ArrayList<ParadigmEntry>();
				entries.add(new ParadigmEntry(result[1], wordEntry.partOfSpeechTag));
				
				if(lemma.endsWith("ý")) {
					entries.add(new ParadigmEntry(result[1], "A"));
					entries.add(new ParadigmEntry(result[1], "G"));
					wordEntry.partOfSpeechFeatures.put("case", "1");
				}
				
				for(ParadigmEntry checkedEntry : entries) {
					if(set.contains(checkedEntry)) {
						switch(checkedEntry.pos) {
						case "G":
						case "A":
							return "atr";
						case "V":
							return "pre";
						case "S":
							return "sub";
						case "D":
							return "adv";
						case "N":
							return "num";
						}
					}
				}
			}
		}

		return "";
	}
}
