package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class SentenceNKE {
	private Map<Integer, List<AbstractAnnotatedWord>> words;
	private List<AbstractAnnotatedWord> sortedWords;
	private String conllText;
	private DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree;
	private String originalSentence;
	
	public SentenceNKE(String conll, Map<Integer, List<AbstractAnnotatedWord>> words) {
		this.conllText = conll;
		this.setWords(words);
	}
	
	public SentenceNKE(String conll, Map<Integer, List<AbstractAnnotatedWord>> words, String originalSentence) {
		this(conll, words);
		this.originalSentence = originalSentence;
	}
	
	public void setWords(Map<Integer, List<AbstractAnnotatedWord>> words) {
		this.words = words;
		sortedWords = new ArrayList<AbstractAnnotatedWord>();
		
		for(List<AbstractAnnotatedWord> list : words.values()) {
			for(AbstractAnnotatedWord word : list) {
				sortedWords.add(word);
			}
		}
		Collections.sort(sortedWords, new Comparator<AbstractAnnotatedWord>() {
			@Override
			public int compare(AbstractAnnotatedWord o1, AbstractAnnotatedWord o2) {
				return o1.order - o2.order;
			}
		});
		IDependencyTreeLayoutBuilder builder = new DependencyTreeLayoutBuilderNaive();
		tree = builder.buildTree(words);
	}
	
	public AbstractAnnotatedWord getWord(int index) {
		if(index < sortedWords.size()) {
			return sortedWords.get(index);
		} else {
			return null;
		}
	}
	
	public List<AbstractAnnotatedWord> getWords() {
		return sortedWords;
	}
	
	public DefaultTreeForTreeLayout<AbstractAnnotatedWord> getTree() {
		return tree;
	}
	
	public String getText() {
		return originalSentence;
	}
	
	public String toString() {
		return conllText;
	}
}
