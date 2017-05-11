package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.List;
import java.util.Map;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class DependencyTreeLayoutCreator implements IDependencyTreeLayoutCreator {

	@Override
	public DefaultTreeForTreeLayout<AbstractAnnotatedWord> buildTree(Map<Integer, List<AbstractAnnotatedWord>> words) {
		List<AbstractAnnotatedWord> roots = words.get(0);
		
//		System.out.println("\\begin{figure}");
//		System.out.println("  \\begin{forest}");
//		System.out.println("    for tree={");
//		System.out.println("      parent anchor=south,");
//		System.out.println("      child anchor=north,");
//		System.out.println("      tier/.wrap pgfmath arg={tier#1}{level()},");
//		System.out.println("      font=\\sffamily,");
//		System.out.println("    }");
		
		for(AbstractAnnotatedWord root : roots) {
			DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree = new DefaultTreeForTreeLayout<AbstractAnnotatedWord>(root);
			buildRecursively(root, words, tree);
//			
//			System.out.println("  \\end{forest}");
//			System.out.println("\\end{figure}");
			
			return tree;
		}
		
		return null;
	}
	
	private void buildRecursively(AbstractAnnotatedWord node, Map<Integer, List<AbstractAnnotatedWord>> words, DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree) {
		List<AbstractAnnotatedWord> nodes = words.get(node.order);
		
		String outWord = node.word.equals(",") ? "/" : node.word;
		
//		System.out.print("[" + outWord + ", name=node" + node.order);
		
		if(nodes != null && !nodes.isEmpty()) {
//			System.out.println("");
			for(AbstractAnnotatedWord word : nodes) {
				tree.addChild(node, word);
				buildRecursively(word, words, tree);
			}
//			System.out.println("]");
		}
//		else {
//			System.out.println("]");
//		}
	}

}
