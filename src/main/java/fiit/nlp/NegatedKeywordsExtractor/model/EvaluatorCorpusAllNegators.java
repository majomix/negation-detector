package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.List;

import fiit.nlp.NegatedKeywordsExtractor.model.core.AbstractAnnotatedWord;
import fiit.nlp.NegatedKeywordsExtractor.model.core.Document;
import fiit.nlp.NegatedKeywordsExtractor.model.core.SentenceNKE;

public class EvaluatorCorpusAllNegators {
	public void evaluate(String name, List<Document> documents) {
		ScoreCalculator calculator = new ScoreCalculator();
		
		for(Document document : documents) {
			for(SentenceNKE sentence : document.getSentences()) {
				for(AbstractAnnotatedWord word : sentence.getWords()) {
					if(checkCurrentWord(word)) {
						if(word.expectedNegator.equals("")) {
							if(word.negator.equals("")) {
								calculator.incrementTrueNegative();
							} else {
								calculator.incrementFalsePositive();
							}
						} else {
							if(word.negator.equals("")) {
								calculator.incrementFalseNegative();
							} else {
								calculator.incrementTruePositive();
							}
						}
					}
				}
			}
		}
		
		System.out.println("4. negatory celkovo");
		System.out.println("Vysledky korpusu " + name + ":");
		System.out.println(calculator.toString());
		System.out.println();
	}

	protected boolean checkCurrentWord(AbstractAnnotatedWord word) {
		return true;
	}
}
