package fiit.nlp.NegatedKeywordsExtractor.model;

import is2.data.SentenceData09;

public class AnnotatedWordSlovak extends AbstractAnnotatedWord {
	
	public AnnotatedWordSlovak(String lemma, String partOfSpeechTag) {
		super();
		
		this.lemma = lemma;
		this.partOfSpeechTag = partOfSpeechTag;
	}
	
	public AnnotatedWordSlovak(SentenceData09 sentence, int id) {
		super();
		
		this.order = Integer.parseInt(sentence.id[id]);
		this.word = sentence.forms[id];
		this.lemma = sentence.lemmas[id];
		this.partOfSpeechTag = sentence.ppos[id];
		
		StringBuilder stringBuilder = new StringBuilder();
		
		for (String feature : sentence.pfeats[id].split("\\|")) {
			String[] parts = feature.split("=");
			partOfSpeechFeatures.put(parts[0], parts[1]);
			if(!("pred_candidate".equals(parts[0]))) {
				stringBuilder.append(parts[1]);
			}
			if("neg".equals(parts[0])) {
				if("a".equals(parts[1])) {
					stringBuilder.append("+");
				} else {
					stringBuilder.append("-");
				}
			}
		}
		
		this.partOfSpeechFeaturesLine = stringBuilder.toString();
		
		this.dependsOn = sentence.pheads[id];
		this.partOfSentence = sentence.plabels[id];
	}
}
