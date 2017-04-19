package fiit.nlp.NegatedKeywordsExtractor.model;

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
		
		tagScope(sentence.getTree(), negator);
		
		// if part of sentence is AuxV, it is a descendant of another word so tag parent's subtree
		if("AuxV".equals(negator.partOfSentence)) {
			tagScope(sentence.getTree(), sentence.getTree().getParent(negator));
		}
	}
	
	private void tagScope(DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree, AbstractAnnotatedWord word) {
		for(AbstractAnnotatedWord node : tree.getChildren(word)) {
			tagScope(tree, node);
		}
		
		// dont tag negator itself nor "nie"
		if(word != originalWord && !(word == previousWord && word.lemma.toLowerCase().equals("nie"))) {
			word.negationTargetOfNode.add(originalWord.order);
		}
	}
}
