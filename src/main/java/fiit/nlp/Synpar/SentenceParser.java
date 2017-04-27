package fiit.nlp.Synpar;
import org.annolab.tt4j.*;

import fiit.nlp.Synpar.Diacritics;
import fiit.nlp.Synpar.Segmentator;
import fiit.nlp.Synpar.Sentence;
import fiit.nlp.Synpar.Spellchecker;
import fiit.nlp.Synpar.Tagger;

import java.io.IOException;
import is2.data.SentenceData09;
import is2.parser.Parser;

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
