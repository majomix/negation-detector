package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractAnnotatedWord {
	public int order;
	public String word;
	public String lemma;
	public String partOfSpeechTag;
	public Map<String, String> partOfSpeechFeatures;
	public String partOfSpeechFeaturesLine;
	public int dependsOn;
	public String partOfSentence;
	public String negator;
	public String expectedNegator;
	public List<Integer> negationTargetOfNode;
	public List<Integer> expectedNegationTargetOfNode;
	
	public AbstractAnnotatedWord() {
		partOfSpeechFeatures = new HashMap<String, String>();
		negationTargetOfNode = new ArrayList<Integer>();
		expectedNegationTargetOfNode = new ArrayList<Integer>();
		
		negator = "";
		expectedNegator = "";
	}

	public String toString() {
		return lemma;
	}
	
	public String getPartOfSpeechTag() {
		return partOfSpeechFeatures.get("pos");
	}
	
	public String getPartOfSpeechCase() {
		return partOfSpeechFeatures.get("case");
	}
	
	public String getPartOfSpeechGender() {
		return partOfSpeechFeatures.get("gen");
	}
	
	public boolean hasPartOfSpeech(String partOfSpeech) {
		if(getPartOfSpeechTag() != null) {
			return getPartOfSpeechTag().equals(partOfSpeech);
		} else return false;
	}
	
	public boolean hasPartOfSpeech(String partOfSpeech, String partOfSpeechTagCase) {
		if(getPartOfSpeechTag() != null && getPartOfSpeechCase() != null) {
			return hasPartOfSpeech(partOfSpeech) && getPartOfSpeechCase().equals(partOfSpeechTagCase);
		} else return false;
	}
}
