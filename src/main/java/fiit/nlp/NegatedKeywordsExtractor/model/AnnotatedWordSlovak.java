package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.HashMap;

import is2.data.SentenceData09;

public class AnnotatedWordSlovak extends AbstractAnnotatedWord {
	
	public AnnotatedWordSlovak(SentenceData09 sentence, int id) {
		this.order = Integer.parseInt(sentence.id[id]);
		this.word = sentence.forms[id];
		this.lemma = sentence.lemmas[id];
		this.partOfSpeechTag = sentence.ppos[id];
		
		partOfSpeechFeatures = new HashMap<String, String>();
		for (String feature : sentence.pfeats[id].split("\\|")) {
			String[] parts = feature.split("=");
			partOfSpeechFeatures.put(parts[0], parts[1]);
		}
		
		this.dependsOn = sentence.pheads[id];
		this.partOfSentence = sentence.plabels[id];
		
		this.negator = "";
		this.expectedNegator = "";
		this.negationTargetOfNode = 0;
		this.expectedNegationTargetOfNode = "";
	}
}
