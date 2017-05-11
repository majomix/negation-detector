package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.Arrays;
import java.util.List;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class ScopeStrategyEnglishPre implements IScopeStrategy {

	@Override
	public void detectScope(SentenceNKE sentence, AbstractAnnotatedWord negator) {
		DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree = sentence.getTree();
		
		for(AbstractAnnotatedWord word : tree.getChildren(negator)) {
			boolean tagSubtree = false;
			for(AbstractAnnotatedWord subWord : tree.getChildren(word)) {
				if(subWord.word.equalsIgnoreCase("to")) {
					tagSubtree = true;
				}
			}
			
			if(tagSubtree) {
				tagScope(tree, word, negator);
				word.negationTargetOfNode.add(negator.order);
			}
		}
	}
	
	private void tagScope(DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree, AbstractAnnotatedWord parent, AbstractAnnotatedWord negator) {
		for(AbstractAnnotatedWord word : tree.getChildren(parent)) {
			tagScope(tree, word, negator);
			word.negationTargetOfNode.add(negator.order);
		}
	}

}
