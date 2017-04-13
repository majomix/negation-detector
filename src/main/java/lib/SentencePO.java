package lib;

import is2.data.SentenceData09;

public class SentencePO {

	public String oneLine;
	public String text;
	public String conll;
	
	public SentencePO() {}
	
	public SentencePO(SentenceData09 sentence) {
		StringBuilder sb = new StringBuilder();
		
		for (String form : sentence.forms) {
			sb.append(form + " ");
		}
		
		text = sb.toString();
		conll = sentence.toString();
		
	}
	
	
	
}
