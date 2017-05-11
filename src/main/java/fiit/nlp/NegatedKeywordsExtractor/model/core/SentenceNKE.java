package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		IDependencyTreeLayoutCreator builder = new DependencyTreeLayoutCreator();
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
	
	public String printLatexTree() {
		StringBuilder stringBuilder = new StringBuilder();
		List<AbstractAnnotatedWord> roots = words.get(0);
			
		stringBuilder.append("\\begin{figure}\n");
		stringBuilder.append("  \\begin{forest}\n");
		stringBuilder.append("    for tree={\n");
		stringBuilder.append("      parent anchor=south,\n");
		stringBuilder.append("      child anchor=north,\n");
		stringBuilder.append("      tier/.wrap pgfmath arg={tier#1}{level()},\n");
		stringBuilder.append("      font=\\sffamily,\n");
		stringBuilder.append("    }\n");

		for(AbstractAnnotatedWord root : roots) {
			buildRecursively(root, words, stringBuilder);
		}
		
		stringBuilder.append("  \\end{forest}\n");
		stringBuilder.append("\\end{figure}\n");
		stringBuilder.append("\\clearpage\n");
		
		return stringBuilder.toString();
	}
		
	private void buildRecursively(AbstractAnnotatedWord node, Map<Integer, List<AbstractAnnotatedWord>> words, StringBuilder stringBuilder) {
		List<AbstractAnnotatedWord> nodes = words.get(node.order);
		
		String outWord = node.word.equals(",") ? "/" : node.word;
		
		stringBuilder.append("[" + outWord + ", name=node" + node.order);
		
		if(nodes != null && !nodes.isEmpty()) {
			stringBuilder.append("\n");
			for(AbstractAnnotatedWord word : nodes) {
				buildRecursively(word, words, stringBuilder);
			}
			stringBuilder.append("]\n");
		} else {
			stringBuilder.append("]\n");
		}
	}
}
