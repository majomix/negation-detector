package fiit.nlp.NegatedKeywordsExtractor.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.annolab.tt4j.TreeTaggerException;
import is2.data.SentenceData09;
import lib.Sentence;
import lib.SentenceParser;

public class TextParserSlovak implements ITextParser {

	@Override
	public void parse(String text, List<SentenceNKE> sentences) {
		SentenceParser parser = ParserLoaderSynpar.getInstance();

		Sentence sentence = new Sentence();
		sentence.text = text;
		sentence.parserType = "betterPreds";

		try {
			SentenceData09[] parsedSentences = parser.parse(sentence);
			for (SentenceData09 currentSentence : parsedSentences) {
				HashMap<Integer, List<AbstractAnnotatedWord>> listOfWords = new HashMap<Integer, List<AbstractAnnotatedWord>>();
				
				for(int i = 0; i < currentSentence.id.length; i++) {
					AbstractAnnotatedWord word = new AnnotatedWordSlovak(currentSentence, i);
					
				    List<AbstractAnnotatedWord> currentValue = listOfWords.get(word.getDependsOn());
				    if (currentValue == null) {
				        currentValue = new ArrayList<AbstractAnnotatedWord>();
				        listOfWords.put(word.getDependsOn(), currentValue);
				    }
				    currentValue.add(word);
				}
				
				SentenceNKE createdSentence = new SentenceNKE(currentSentence.toString(), listOfWords);
				sentences.add(createdSentence);
			}
		} catch (IOException | TreeTaggerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void detectNegators(List<SentenceNKE> sentences, INegativePrefixStrategy strategy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detectNegators(List<SentenceNKE> sentences) {
		// TODO Auto-generated method stub
		
	}
}
