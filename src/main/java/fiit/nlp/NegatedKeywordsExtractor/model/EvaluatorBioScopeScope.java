package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import fiit.nlp.NegatedKeywordsExtractor.model.core.AbstractAnnotatedWord;
import fiit.nlp.NegatedKeywordsExtractor.model.core.Document;
import fiit.nlp.NegatedKeywordsExtractor.model.core.SentenceNKE;

public class EvaluatorBioScopeScope {

	public void evaluate(String name, List<Document> documents) {
		ScoreCalculator score = new ScoreCalculator();
		
		for(Document document : documents) {
			for(SentenceNKE sentence : document.getSentences()) {
				List<AbstractAnnotatedWord> negators = new ArrayList<AbstractAnnotatedWord>();
				
				for(AbstractAnnotatedWord word : sentence.getWords()) {
					if(!("".equals(word.expectedNegator)) || (!("".equals(word.negator)) && checkCurrentWord(word))) {
						negators.add(word);
					}
				}
				
				for(AbstractAnnotatedWord negator : negators) {

					for(AbstractAnnotatedWord word : sentence.getWords()) {
						if(!word.hasPartOfSpeech("Z")) {
							if(word.negationTargetOfNode.contains(negator.order)) {
								if(word.expectedNegationTargetOfNode.contains(negator.order)) {
									score.incrementTruePositive();
								} else {
									score.incrementFalsePositive();
								}
							} else {
								if(word.expectedNegationTargetOfNode.contains(negator.order)) {
									score.incrementFalseNegative();
								} else {
									score.incrementTrueNegative();
								}
							}
							
						}
					}
				}
			}
		}
		
		System.out.println("3. negacie");
		System.out.println("Vysledky korpusu " + name + ":");
		System.out.println(score.toString());
		
		System.out.println();
	}
	
	protected boolean checkCurrentWord(AbstractAnnotatedWord word) {
		List<String> negationTypes = Arrays.asList("sbs", "adv", "att");
		return !negationTypes.contains(word.negator);
	}
}
