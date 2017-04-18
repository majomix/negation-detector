package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractAnnotatedWord {
	protected int order;
	protected String word;
	protected String lemma;
	protected String partOfSpeechTag;
	protected Map<String, String> partOfSpeechFeatures;
	protected int dependsOn;
	protected String partOfSentence;
	protected String negator;
	protected String expectedNegator;
	protected List<Integer> negationTargetOfNode;
	protected List<Integer> expectedNegationTargetOfNode;
	
	protected AbstractAnnotatedWord() {
		partOfSpeechFeatures = new HashMap<String, String>();
		negationTargetOfNode = new ArrayList<Integer>();
		expectedNegationTargetOfNode = new ArrayList<Integer>();
		
		negator = "";
		expectedNegator = "";
	}

	public String toString() {
		return lemma;
	}
	
	protected String getPartOfSpeechTag() {
		return partOfSpeechTag.substring(0, 1);
	}
	
	protected boolean hasPartOfSpeech(String partOfSpeech) {
		return partOfSpeechTag.substring(0, 1).equals(partOfSpeech);
	}
	
	protected boolean hasPartOfSpeech(String partOfSpeech, String partOfSpeechTagCase) {
		return hasPartOfSpeech(partOfSpeech) && partOfSpeechTag.substring(3, 4).equals(partOfSpeechTagCase);
	}
}
