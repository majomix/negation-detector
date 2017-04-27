package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.HashMap;
import java.util.List;

import fiit.nlp.NegatedKeywordsExtractor.model.core.AbstractAnnotatedWord;
import fiit.nlp.NegatedKeywordsExtractor.model.core.Document;
import fiit.nlp.NegatedKeywordsExtractor.model.core.SentenceNKE;

public class EvaluatorCorpusNegationStatistics {
	public void evaluate(String name, List<Document> documents) {
		HashMap<String, Integer> numbers = new HashMap<String, Integer>();
		String[] negationTypes = new String[] { "pre", "gen", "atr", "sub", "nie", "sbs", "adv", "num" };
		
		for(String type : negationTypes) {
			numbers.put(type, 0);
		}

		for(Document document : documents) {
			for(SentenceNKE sentence : document.getSentences()) {
				for(AbstractAnnotatedWord word : sentence.getWords()) {
					if(!("".equals(word.expectedNegator))) {
						Integer value = numbers.get(word.expectedNegator);
						if(value != null) {
							numbers.put(word.expectedNegator, value + 1);
						} else {
							System.out.println("!!!!!!!!!!!!!!! " + word.expectedNegator);
						}
						
					}
				}
			}
		}
		
		System.out.println("3. negacie");
		System.out.println("Vysledky korpusu " + name + ":");

		for(String type : negationTypes) {
			System.out.println(type + " - " + numbers.get(type));
		}
		
		System.out.println();
	}
}
