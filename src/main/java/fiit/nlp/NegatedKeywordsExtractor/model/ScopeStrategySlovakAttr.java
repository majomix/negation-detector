package fiit.nlp.NegatedKeywordsExtractor.model;

public class ScopeStrategySlovakAttr implements IScopeStrategy {

	@Override
	public void detectScope(SentenceNKE sentence, AbstractAnnotatedWord negator) {
		AbstractAnnotatedWord node = negator;
		AbstractAnnotatedWord parent;
		boolean found = false;
		
		// direct ancestor
		while((parent = sentence.getTree().getParent(node)) != null) {
			if(parent.hasPartOfSpeech("S", negator.getPartOfSpeechTag())) {
				parent.negationTargetOfNode.add(negator.order);
				found = true;
				break;
			}
		};
		
		// subject
		if(!found) {
			for(AbstractAnnotatedWord word : sentence.getWords()) {
				if(word.partOfSentence.equals("Subj")) {
					word.negationTargetOfNode.add(negator.order);
					negator.negator = "sub";
					break;
				}
			}
		}
	}

}
