package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.util.*;

public class Document {
	private String rawText;
	private String filename;
	private String language;
	private List<SentenceNKE> sentences;
	
	private ITextParser parser;
	private INegativePrefixStrategy strategy;
	
	private Document(String rawText, String language) {
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
	}
	
	public List<SentenceNKE> getSentences() {
		return sentences;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setRawText(String rawText) {
		this.rawText = rawText;
	}
	
	public void setLanguage(String language) {
		if(language.equals("en")) {
			parser = new TextParserEnglish();
		} else {
			parser = new TextParserSlovak();
			strategy = new NegativePrefixSlovakParadigmsStrategy();
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
