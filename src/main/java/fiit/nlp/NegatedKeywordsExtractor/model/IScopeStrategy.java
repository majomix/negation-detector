package fiit.nlp.NegatedKeywordsExtractor.model;

public interface IScopeStrategy {
	void detect(SentenceNKE sentence, AbstractAnnotatedWord word);
}
