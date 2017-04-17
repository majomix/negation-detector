package fiit.nlp.NegatedKeywordsExtractor.model;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class ScopeStrategySlovakPred implements IScopeStrategy {
	@Override
	public void detect(SentenceNKE sentence, AbstractAnnotatedWord word) {
		DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree = sentence.getTree();
		tag(tree, word);
	}
	
	private void tag(DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree, AbstractAnnotatedWord word) {
		for(AbstractAnnotatedWord node : tree.getChildren(word)) {
			tag(tree, node);
		}
		
		word.negationTargetOfNode = 5;
	}
}
