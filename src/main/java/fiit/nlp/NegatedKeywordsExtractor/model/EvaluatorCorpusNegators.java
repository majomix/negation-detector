package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.ArrayList;
import java.util.List;

public class EvaluatorCorpusNegators {

	public void evaluate(String name, List<Document> documents) {
		NegativePrefixSlovakParadigmsStrategy strategy = new NegativePrefixSlovakParadigmsStrategy();
		ScoreCalculator neCalculator = new ScoreCalculator();
		ScoreCalculator otherCalculator = new ScoreCalculator();
		
		for(Document document : documents) {
			for(SentenceNKE sentence : document.getSentences()) {
				for(AbstractAnnotatedWord word : sentence.getWords()) {
					String[] result = word.word.toLowerCase().split("(?<=^(" + String.join("|", strategy.getPrefixes()) + "))");
					
					if(result.length == 1) {
						continue;
					}
					
					if(result[0].equals("ne")) {
						calculateScore(neCalculator, word);
					} else {
						calculateScore(otherCalculator, word);
					}
				}
			}
		}
		
		System.out.println("2. negatory");
		System.out.println("Vysledky korpusu " + name + ":");
		System.out.println(neCalculator.toString());
		System.out.println(otherCalculator.toString());
		System.out.println();
	}
	
	private void calculateScore(ScoreCalculator calculator, AbstractAnnotatedWord word) {
		// either TP or TN
		if(word.expectedNegator.equals(word.negator)) {
			if(word.expectedNegator.equals("")) {
				calculator.incrementTrueNegative();
			} else {
				calculator.incrementTruePositive();
			}
		} else {
			if(word.expectedNegator.equals("")) {
				calculator.incrementFalsePositive();
			} else {
				calculator.incrementFalseNegative();
			}
		}
	}
}
