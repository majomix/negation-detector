package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.HashMap;
import java.util.Map;
import is2.data.SentenceData09;

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
	protected int negationTargetOfNode;
	protected String expectedNegationTargetOfNode;
	
	public int getOrder() {
		return order;
	}
	
	public String getLemma() {
		return lemma;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getDependsOn() {
		return dependsOn;
	}
	
	public String toString() {
		return lemma;
	}
}
