package lib;
import lib.Segmentator;
import lib.Sentence;
import org.annolab.tt4j.*;

import java.io.IOException;
import is2.data.SentenceData09;
import is2.parser.Parser;
import lib.Tagger;
import lib.Diacritics;
import lib.Spellchecker;

public class SentenceParser {
	
	Parser normalParser;
	Parser predParser;
	Tagger tagger;
	Spellchecker speller;
	
	public SentenceParser() throws IOException {
		predParser = new Parser("F:/Java/lib/model-pred");
		
		tagger = new Tagger();
		speller = new Spellchecker();
	}

	public SentenceData09[] parse(Sentence sentence) throws IOException, TreeTaggerException {
		Parser targetParser;
		
		if (sentence.diacritics != null) {
			sentence.text = Diacritics.Reconstruct(sentence.text);
		}
		
		if (sentence.spellchecking != null) {
			sentence.text = speller.check(sentence.text);
		}
		
		if (sentence.parserType.equals("betterPreds")) {
			targetParser = predParser;
		}
//		else if (sentence.parserType.equals("betterPnoms")) {
//			targetParser = pnomParser;
//		}
		else {
			targetParser = normalParser;
		}
		
		String[] rawSentences = Segmentator.getInstance().getSentences(sentence.text);
		
		SentenceData09[] result = new SentenceData09[rawSentences.length];
		
		for (int i = 0; i < rawSentences.length; i++) {
			Sentence sent = new Sentence();
			sent.text = rawSentences[i];
			tagger.tagSentence(sent);
						
			SentenceData09 it = sent.toConll(sentence.parserType.equals("betterPreds"));
			
			SentenceData09 parsed = targetParser.parse(it,targetParser.params,false,targetParser.options);
			
			result[i] = parsed;
		}
		return result;
	}
	
}
