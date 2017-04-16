package fiit.nlp.NegatedKeywordsExtractor.model;

public class NegatorDetectorSlovak {
	public void detect(AbstractAnnotatedWord wordEntry) {
		
		String word = wordEntry.word.toLowerCase();
		
		if(word.equals("bez") || word.equals("okrem") || word.equals("mimo")) {
			wordEntry.negator = "gen";
		} else if(word.equals("nie")) {
			wordEntry.negator = "nie";
		} else {
			NegativePrefixSlovakParadigmsStrategy detector = new NegativePrefixSlovakParadigmsStrategy();
			detector.detect(wordEntry);
		}

	}
}
