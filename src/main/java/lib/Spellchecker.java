package lib;

import java.util.List;

import opennlp.tools.tokenize.SimpleTokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spellchecker {
	SimpleTokenizer tokenizer;
	
	public Spellchecker() {
		tokenizer = new SimpleTokenizer();
	}
	
	public String check(String input) {
		String[] tokens = tokenizer.tokenize(input);
		
		StringBuilder sb = new StringBuilder();
		for (String word : tokens) {
			sb.append(word + " ");
		}
		
		return sb.toString();
	}
}
