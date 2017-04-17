package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.*;

public class Document {
	private String rawText;
	private String filename;
	private String language;
	
	private List<SentenceNKE> sentences;
	private Map<String, Integer> histogram;
	
	private ITextParser parser;
	private INegativePrefixStrategy strategy;
	
	private Document(String rawText, String language) {
		histogram = new HashMap<String, Integer>();
		strategy = new NegativePrefixSlovakParadigmsStrategy();
		
		this.setLanguage(language);
		this.setRawText(rawText);
		strategy = null;
	}
	
	public Document(String rawText, List<SentenceNKE> sentences, String language) {
		this(rawText, language);
		this.sentences = sentences;
		parser.detectNegators(sentences);
		parser.detectNegationScope(sentences);
	}
	
	public Document(String rawText, String language, String filename) {
		this(rawText, language);
		this.filename = filename;
		initializeParsing();
//		Integer count = histogram.get(word.getLemma());
//		histogram.put(word.getLemma(), count == null ? 1 : count + 1);
	}
	
	public List<SentenceNKE> getSentences() {
		return sentences;
	}
	
	public Map<String, Integer> getHistogram() {
		return histogram;
	}
	
	public void setRawText(String rawText) {
		this.rawText = rawText;
	}
	
	public void setLanguage(String language) {
		if(language.equals("en")) {
			parser = new TextParserEnglish();
		} else {
			parser = new TextParserSlovak();
		}
		
		this.language = language;
	}
	
	public void initializeParsing() {
		if(parser != null && rawText != null) {
			sentences = new ArrayList<SentenceNKE>();
			parser.parse(rawText, sentences);
			parser.detectNegators(sentences, strategy);
			parser.detectNegationScope(sentences);
		}
	}
}
