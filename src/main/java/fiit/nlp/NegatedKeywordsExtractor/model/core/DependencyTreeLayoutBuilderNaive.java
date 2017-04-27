package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class DependencyTreeLayoutBuilderNaive implements IDependencyTreeLayoutBuilder {

	@Override
	public DefaultTreeForTreeLayout<AbstractAnnotatedWord> buildTree(Map<Integer, List<AbstractAnnotatedWord>> words) {
		List<AbstractAnnotatedWord> roots = words.get(0);
		
		for(AbstractAnnotatedWord root : roots) {
			DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree = new DefaultTreeForTreeLayout<AbstractAnnotatedWord>(root);
			buildRecursively(root, words, tree);
			
			return tree;
		}
		
		System.out.println("");
		
		return null;
	}
	
	private void buildRecursively(AbstractAnnotatedWord node, Map<Integer, List<AbstractAnnotatedWord>> words, DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree) {
		List<AbstractAnnotatedWord> nodes = words.get(node.order);
		
		String outWord = node.word.equals(",") ? "/" : node.word;
		
		System.out.print("[" + outWord + ", name=node" + node.order);
		
		if(nodes != null && !nodes.isEmpty()) {
			System.out.println("");
			for(AbstractAnnotatedWord word : nodes) {
				tree.addChild(node, word);
				buildRecursively(word, words, tree);
			}
			System.out.println("]");
		} else {
			System.out.println("]");
		}
	}

}
