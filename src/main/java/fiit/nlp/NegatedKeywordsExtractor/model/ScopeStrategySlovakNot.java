package fiit.nlp.NegatedKeywordsExtractor.model;

public class ScopeStrategySlovakNot implements IScopeStrategy {

	@Override
	public void detectScope(SentenceNKE sentence, AbstractAnnotatedWord negator) {
		
		// "nie je" - predicative negation
		AbstractAnnotatedWord followingWord = sentence.getWord(negator.order);
		if(followingWord.word.toLowerCase().equals("je")) {
			followingWord.negationTargetOfNode.add(negator.order);
			followingWord.negator = "pre";
		}
		// other - clausal negation
		else {
			// right neighbour
			AbstractAnnotatedWord parent = sentence.getTree().getParent(negator);
			boolean tag = false;
			for(AbstractAnnotatedWord word : sentence.getTree().getChildren(parent)) {
				if(tag) {
					word.negationTargetOfNode.add(negator.order);
					break;
				}
				if(word == negator) {
					tag = true;
				}
			}
		}
	}

}
