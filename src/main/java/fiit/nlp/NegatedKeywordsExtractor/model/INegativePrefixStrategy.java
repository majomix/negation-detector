package fiit.nlp.NegatedKeywordsExtractor.model;

public interface INegativePrefixStrategy {
	void detect(AbstractAnnotatedWord wordEntry);
}
