package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class SentenceNKE {
	private Map<Integer, List<AbstractAnnotatedWord>> words;
	private String conllText;
	private DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree;
	
	public SentenceNKE(String conll, Map<Integer, List<AbstractAnnotatedWord>> words) {
		this.conllText = conll;
		this.setWords(words);
	}
	
	public void setWords(Map<Integer, List<AbstractAnnotatedWord>> words) {
		this.words = words;
		IDependencyTreeLayoutBuilder builder = new DependencyTreeLayoutBuilderNaive();
		tree = builder.buildTree(words);
	}
	
	public String toString() {
		return conllText;
	}
}
