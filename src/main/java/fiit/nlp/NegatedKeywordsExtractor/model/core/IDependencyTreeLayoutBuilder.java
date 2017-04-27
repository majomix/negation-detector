package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.List;
import java.util.Map;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public interface IDependencyTreeLayoutBuilder {
	DefaultTreeForTreeLayout<AbstractAnnotatedWord> buildTree(Map<Integer, List<AbstractAnnotatedWord>> words);
}
