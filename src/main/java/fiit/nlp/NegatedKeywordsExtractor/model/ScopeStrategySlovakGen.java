package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class ScopeStrategySlovakGen implements IScopeStrategy {
	private ArrayList<Tuple<Integer, AbstractAnnotatedWord>> listOfPairs;
	
	@Override
	public void detectScope(SentenceNKE sentence, AbstractAnnotatedWord negator) {
		listOfPairs = new ArrayList<Tuple<Integer, AbstractAnnotatedWord>>();
		buildListOfScopeCandidates(sentence.getTree(), negator, 1);
		
		Collections.sort(listOfPairs, new Comparator<Tuple<Integer, AbstractAnnotatedWord>>() {
			@Override
			public int compare(Tuple<Integer, AbstractAnnotatedWord> o1, Tuple<Integer, AbstractAnnotatedWord> o2) {
				return o1.x - o2.x;
			}
		});
		
		int minDepth = listOfPairs.get(0).x;
		
		for(Tuple<Integer, AbstractAnnotatedWord> tuple : listOfPairs) {
			if(tuple.x == minDepth) {
				tuple.y.negationTargetOfNode.add(negator.order);
			}
		}
	}
	
	private void buildListOfScopeCandidates(DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree, AbstractAnnotatedWord word, int depth) {
		if(depth > 1) {
			if(word.hasPartOfSpeech("S") || word.hasPartOfSpeech("P")) {
				listOfPairs.add(new Tuple<Integer, AbstractAnnotatedWord>(depth, word));
			}
		}

		for(AbstractAnnotatedWord node : tree.getChildren(word)) {
			buildListOfScopeCandidates(tree, node, depth + 1);
		}
	}
	
	public class Tuple<X, Y> {
		public final X x;
		public final Y y;
		
		public Tuple(X x, Y y) {
			this.x = x;
			this.y = y;
		}
	} 
}
