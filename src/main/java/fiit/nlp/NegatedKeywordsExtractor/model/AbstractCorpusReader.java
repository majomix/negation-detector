package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.List;

public abstract class AbstractCorpusReader {
	protected List<String> unparsable;
	public abstract List<Document> createCorpus();
	
	public List<String> getUnparsable() {
		return unparsable;
	}
}
