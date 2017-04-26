package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EvaluatorCorpusNegationScope {
	private List<HashMap<String, ScoreCalculator>> allScores = new ArrayList<HashMap<String, ScoreCalculator>>();	
	
	public void printAllResults() {
		String[] negationTypes = new String[] { "pre", "gen", "atr", "sub", "nie", "sbs", "adv", "num" };
		System.out.println("Vysledky vsetkych korpusov:");
		
		for(String type : negationTypes) {
			int truePositive = 0;
			int trueNegative = 0;
			int falsePositive = 0;
			int falseNegative = 0;
			
			for(HashMap<String, ScoreCalculator> score : allScores) {
				ScoreCalculator calculator = score.get(type);
				truePositive += calculator.getTruePositive();
				trueNegative += calculator.getTrueNegative();
				falsePositive += calculator.getFalsePositive();
				falseNegative += calculator.getFalseNegative();
			}
			
			ScoreCalculator totalScore = new ScoreCalculator(truePositive, trueNegative, falsePositive, falseNegative);
			
			System.out.println(type + " - " + totalScore);
		}

		
		System.out.println();
	}
	
	public void evaluate(String name, List<Document> documents) {
		HashMap<String, ScoreCalculator> scores = new HashMap<String, ScoreCalculator>();
		String[] negationTypes = new String[] { "pre", "gen", "atr", "sub", "nie", "sbs", "adv", "num" };
		
		for(String type : negationTypes) {
			scores.put(type, new ScoreCalculator());
		}

		for(Document document : documents) {
			for(SentenceNKE sentence : document.getSentences()) {
				List<AbstractAnnotatedWord> negators = new ArrayList<AbstractAnnotatedWord>();
				
				for(AbstractAnnotatedWord word : sentence.getWords()) {
					if(!("".equals(word.expectedNegator)) || !("".equals(word.negator))) {
						negators.add(word);
					}
				}
				
				for(AbstractAnnotatedWord negator : negators) {
					String type = negator.negator;
					ScoreCalculator score = scores.get(negator.negator);
					if(score == null) {
						score = scores.get(negator.expectedNegator);
						type = negator.expectedNegator;
					}
					
					if(score != null) {
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
								
								scores.put(type, score);
							}

						}
					} else {
						System.out.println("!!!!!!!!!!!!!!! " + negator.negator + negator.expectedNegator);
					}

				}
			}
		}
		
		System.out.println("3. negacie");
		System.out.println("Vysledky korpusu " + name + ":");

		for(String type : negationTypes) {
			System.out.println(type + " - " + scores.get(type));
		}
		
		System.out.println();
		
		allScores.add(scores);
	}
}
