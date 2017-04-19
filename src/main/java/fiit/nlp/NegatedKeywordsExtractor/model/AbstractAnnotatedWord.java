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
		return partOfSpeechFeatures.get("pos");
	}
	
	protected String getPartOfSpeechCase() {
		return partOfSpeechFeatures.get("case");
	}
	
	protected String getPartOfSpeechGender() {
		return partOfSpeechFeatures.get("gen");
	}
	
	protected boolean hasPartOfSpeech(String partOfSpeech) {
		if(getPartOfSpeechTag() != null) {
			return getPartOfSpeechTag().equals(partOfSpeech);
		} else return false;
		
	}
	
	protected boolean hasPartOfSpeech(String partOfSpeech, String partOfSpeechTagCase) {
		if(getPartOfSpeechTag() != null && getPartOfSpeechCase() != null) {
			return hasPartOfSpeech(partOfSpeech) && getPartOfSpeechCase().equals(partOfSpeechTagCase);
		} else return false;
	}
}
