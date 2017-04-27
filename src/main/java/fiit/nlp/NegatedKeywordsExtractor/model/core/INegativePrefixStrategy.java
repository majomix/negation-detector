package fiit.nlp.NegatedKeywordsExtractor.model.core;

public interface INegativePrefixStrategy {
	String detect(AbstractAnnotatedWord wordEntry);
}
