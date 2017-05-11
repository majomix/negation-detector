package fiit.nlp.NegatedKeywordsExtractor.model.core;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class ScopeStrategySlovakAttr implements IScopeStrategy {

	@Override
	public void detectScope(SentenceNKE sentence, AbstractAnnotatedWord negator) {
		DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree = sentence.getTree();
		AbstractAnnotatedWord node = negator;
		AbstractAnnotatedWord parent;
		boolean found = false;
		
		while((parent = tree.getParent(node)) != null && !found) {
			node = parent;
			
			// ancestor
			if(parent.hasPartOfSpeech("S", negator.getPartOfSpeechCase())) {
				parent.negationTargetOfNode.add(negator.order);
				found = true;
			} else {
				// subject
				for(AbstractAnnotatedWord word : tree.getChildren(node)) {
					if(word.partOfSentence.equals("Sb") && word.hasPartOfSpeech("S", negator.getPartOfSpeechCase())) {
						word.negationTargetOfNode.add(negator.order);
						negator.negator = "sub";
						found = true;
						break;
					}
				}
			}
		}
	}

}
