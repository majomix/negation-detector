package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.List;

import fiit.nlp.NegatedKeywordsExtractor.model.core.AbstractAnnotatedWord;
import fiit.nlp.NegatedKeywordsExtractor.model.core.Document;
import fiit.nlp.NegatedKeywordsExtractor.model.core.SentenceNKE;

public class EvaluatorStatistics {

	public void evaluate(List<Document> documents) {
		evaluate("Unnamed", documents);
	}
	
	public void evaluate(String name, List<Document> documents) {
		int sentences = 0;
		int negatedSentences = 0;
		int negations = 0;
		int words = 0;
		int doubleNegations = 0;
		int punctuation = 0;
		int[] histogram = new int[256];
		int shortSentences = 0;
		int mediumSentences = 0;
		int longSentences = 0;
		int extraLongSentences = 0;
		
		for(Document document : documents) {
			sentences += document.getSentences().size();

			for(SentenceNKE sentence : document.getSentences()) {
				boolean negation = false;
				boolean doubleNegation = false;

				int sentenceWords = sentence.getWords().size();
				words += sentenceWords;
				histogram[sentenceWords]++;
				
				for(AbstractAnnotatedWord word : sentence.getWords()) {
					
					if(!("".equals(word.expectedNegator))) {
						negation = true;
						negations++;
					}
					
					if(word.expectedNegationTargetOfNode.size() > 1) {
						doubleNegation = true;
					}
					
					if(word.hasPartOfSpeech("Z")) {
						punctuation++;
					}
				}
				
				if(negation) {
					negatedSentences++;
				}
				
				if(doubleNegation) {
					doubleNegations++;
				}
			}
		}
		
		for(int i = 0; i < 256; i++) {
			if(i < 6) {
				shortSentences += histogram[i];
			} else if(i >= 6 && i < 20) {
				mediumSentences += histogram[i];
			} else if(i >= 20 && i < 50) {
				longSentences += histogram[i];
			} else {
				extraLongSentences += histogram[i];
			}
		}
		
		System.out.println("1. statistika");
		System.out.println("Vysledky korpusu " + name + ":");
		System.out.println("Viet " + sentences);
		System.out.println("Slov " + words);
		System.out.println("Negovanych viet " + negatedSentences);
		System.out.println("Negacii " + negations);
		System.out.println("Negacii na 1 vetu " + ((double) negations / sentences));
		System.out.println("Dvojitych negacii " + doubleNegations);
		System.out.println("Interpunkcie " + punctuation);
		System.out.println("Interpunkcie na 1 vetu " + ((double) punctuation / sentences));
		System.out.println("Kratkych viet " + shortSentences);
		System.out.println("Stredne dlhych viet " + mediumSentences);
		System.out.println("Dlhych viet " + longSentences);
		System.out.println("Velmi dlhych viet " + extraLongSentences);
		System.out.println();
	}
}
