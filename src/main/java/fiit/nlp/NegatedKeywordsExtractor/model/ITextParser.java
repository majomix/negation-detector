package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.List;
import java.util.Map;

public interface ITextParser {
	void parse(String text, List<SentenceNKE> sentences);
	void detectNegators(List<SentenceNKE> sentences);
	void detectNegators(List<SentenceNKE> sentences, INegativePrefixStrategy strategy);
}
