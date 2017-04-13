package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.HashMap;

public class AnnotatedWordEnglish extends AbstractAnnotatedWord {

	public AnnotatedWordEnglish(String annotatedWord) {
		String[] tokens = annotatedWord.split("\t");
		
		this.order = Integer.parseInt(tokens[0]);
		this.word = tokens[1];
		this.lemma = tokens[2];
		this.partOfSpeechTag = tokens[3];
		
		partOfSpeechFeatures = new HashMap<String, String>();
		partOfSpeechFeatures.put("entity", tokens[4]);
		
		this.dependsOn = Integer.parseInt(tokens[5]);
		this.partOfSentence = tokens[6];
		
		this.negator = false;
		this.negationTargetOfNode = 0;
	}
}
