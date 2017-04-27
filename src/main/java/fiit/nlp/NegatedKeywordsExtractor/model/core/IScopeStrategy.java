package fiit.nlp.NegatedKeywordsExtractor.model.core;

public interface IScopeStrategy {
	void detectScope(SentenceNKE sentence, AbstractAnnotatedWord negator);
}
