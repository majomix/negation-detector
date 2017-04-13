package fiit.nlp.NegatedKeywordsExtractor.model;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Document {
	private String rawText;
	private String filename;
	private String language;
	
	private List<SentenceNKE> sentences;
	private Map<String, Integer> histogram;
	
	public Document(String rawText, String filename, String language) {
		sentences = new ArrayList<SentenceNKE>();
		histogram = new HashMap<String, Integer>();
		
		this.filename = filename;
		this.language = language;
		this.setRawText(rawText);
	}
	
	public List<SentenceNKE> getSentences() {
		return sentences;
	}
	
	public Map<String, Integer> getHistogram() {
		return histogram;
	}
	
	private void setRawText(String rawText) {
		this.rawText = rawText;
		
		ITextParser parser;
		
		if(language.equals("en")) {
			parser = new TextParserEnglish();
			
		} else {
			parser = new TextParserSlovak();
		}
		
		parser.parse(rawText, sentences, histogram);
		
		WordDictionaryLoaderParadigms.getInstance();
	}
}
