package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.Arrays;
import java.util.List;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class ScopeStrategyEnglishCpd implements IScopeStrategy {

	@Override
	public void detectScope(SentenceNKE sentence, AbstractAnnotatedWord negator) {
		AbstractAnnotatedWord parent = sentence.getTree().getParent(negator);

		if(parent != null) {
			parent.negationTargetOfNode.add(negator.order);
			tagScopeInRightSiblings(sentence.getTree(), parent, negator, false);
		}
	}
	
	private void tagScopeInRightSiblings(DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree, AbstractAnnotatedWord parent, AbstractAnnotatedWord negator, boolean tag) {
		List<String> conjuctions = Arrays.asList("because", "nor");
		
		for(AbstractAnnotatedWord word : tree.getChildren(parent)) {
			if(tag && !conjuctions.contains(word.word.toLowerCase())) {
				tagScopeInRightSiblings(tree, word, negator, tag);
				word.negationTargetOfNode.add(negator.order);
			}
			if(word == negator) {
				tag = true;
			}
		}
	}

}
