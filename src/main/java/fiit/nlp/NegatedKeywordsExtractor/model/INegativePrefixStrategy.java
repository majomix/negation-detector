package fiit.nlp.NegatedKeywordsExtractor.model;

public interface INegativePrefixStrategy {
	String detect(AbstractAnnotatedWord wordEntry);
}
