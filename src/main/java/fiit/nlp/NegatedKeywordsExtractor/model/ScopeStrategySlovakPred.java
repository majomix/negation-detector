package fiit.nlp.NegatedKeywordsExtractor.model;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class ScopeStrategySlovakPred implements IScopeStrategy {
	private AbstractAnnotatedWord originalWord;
	
	@Override
	public void detectScope(SentenceNKE sentence, AbstractAnnotatedWord negator) {
		originalWord = negator;
		tagScope(sentence.getTree(), negator);
	}
	
	private void tagScope(DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree, AbstractAnnotatedWord word) {
		for(AbstractAnnotatedWord node : tree.getChildren(word)) {
			tagScope(tree, node);
		}
		
		if(word != originalWord) {
			word.negationTargetOfNode.add(originalWord.order);
		}
	}
}
