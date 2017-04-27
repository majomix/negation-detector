package fiit.nlp.NegatedKeywordsExtractor.model.core;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class ScopeStrategySlovakPred implements IScopeStrategy {
	private AbstractAnnotatedWord originalWord;
	private AbstractAnnotatedWord previousWord;
	
	@Override
	public void detectScope(SentenceNKE sentence, AbstractAnnotatedWord negator) {
		originalWord = negator;
		
		// check for "nie"
		if(negator.order >= 2) {
			previousWord = sentence.getWord(negator.order - 2);
		}
		
		// if part of sentence is AuxV, the negator's parent is tagged as predicate so tag parent's subtree
		if("AuxV".equals(negator.partOfSentence)) {
			tagScope(sentence.getTree(), sentence.getTree().getParent(negator));
		}
		// otherwise tag negator's subtree
		else {
			tagScope(sentence.getTree(), negator);
		}
	}
	
	private void tagScope(DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree, AbstractAnnotatedWord word) {
		for(AbstractAnnotatedWord node : tree.getChildren(word)) {
			// dont tag specific conjunctions's subtrees
			boolean tag = true;
			String[] ignoreConjunctions = new String[] { "ale", "takže", "pretože", "keďže", "keď", "aby", "pokiaľ", "nakoľko" };
			for(String conjunction : ignoreConjunctions) {
				if(word.word.equalsIgnoreCase(conjunction)) {
					tag = false;
					break;
				}
			}
			
			if(tag) {
				tagScope(tree, node);
			}
		}
		
		// dont tag negator itself nor "nie"
		if(word != null && word != originalWord && !(word == previousWord && word.lemma.toLowerCase().equals("nie"))) {
			word.negationTargetOfNode.add(originalWord.order);
		}
	}
}
