package fiit.nlp.NegatedKeywordsExtractor.model;

public interface IScopeStrategy {
	void detectScope(SentenceNKE sentence, AbstractAnnotatedWord negator);
}
