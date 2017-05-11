package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.List;

import fiit.nlp.NegatedKeywordsExtractor.model.core.Document;

public interface IEvaluator {
	String evaluate(String name, List<Document> documents);
	String printAll();
}
