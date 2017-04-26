package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.List;

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
		
		for(Document document : documents) {
			sentences += document.getSentences().size();

			for(SentenceNKE sentence : document.getSentences()) {
				boolean negation = false;
				boolean doubleNegation = false;
				
				for(AbstractAnnotatedWord word : sentence.getWords()) {
					words++;
					
					if(!("".equals(word.expectedNegator))) {
						negation = true;
						negations++;
					}
					
					if(word.expectedNegationTargetOfNode.size() > 1) {
						doubleNegation = true;
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
		
		System.out.println("1. statistika");
		System.out.println("Vysledky korpusu " + name + ":");
		System.out.println("Viet " + sentences);
		System.out.println("Slov " + words);
		System.out.println("Negovanych viet " + negatedSentences);
		System.out.println("Negacii " + negations);
		System.out.println("Negacii na 1 vetu " + ((double) negations / sentences));
		System.out.println("Dvojitych negacii " + doubleNegations);
		System.out.println();
	}
}
