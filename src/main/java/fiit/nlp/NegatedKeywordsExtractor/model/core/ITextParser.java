package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.List;

public interface ITextParser {
	void parse(String text, List<SentenceNKE> sentences);
	void detectNegators(List<SentenceNKE> sentences);
	void detectNegators(List<SentenceNKE> sentences, INegativePrefixStrategy strategy);
	void detectNegationScope(List<SentenceNKE> sentences);
}
