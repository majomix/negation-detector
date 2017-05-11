package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.annolab.tt4j.TreeTaggerException;

import fiit.nlp.Synpar.Sentence;
import fiit.nlp.Synpar.SentenceParser;
import is2.data.SentenceData09;

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
					
				    List<AbstractAnnotatedWord> currentValue = listOfWords.get(word.dependsOn);
				    if (currentValue == null) {
				        currentValue = new ArrayList<AbstractAnnotatedWord>();
				        listOfWords.put(word.dependsOn, currentValue);
				    }
				    currentValue.add(word);
				}
				
				SentenceNKE createdSentence = new SentenceNKE(currentSentence.toString(), listOfWords, String.join(" ", currentSentence.forms));
				sentences.add(createdSentence);
			}
		} catch (IOException | TreeTaggerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void detectNegators(List<SentenceNKE> sentences, INegativePrefixStrategy strategy) {
		if(strategy == null) {
			detectNegators(sentences);
			return;
		}
		
		for(SentenceNKE sentence : sentences) {
			for(AbstractAnnotatedWord wordEntry : sentence.getWords()) {
				String word = wordEntry.lemma.toLowerCase();
				
				if(word.equalsIgnoreCase("bez") || word.equalsIgnoreCase("okrem") || word.equalsIgnoreCase("mimo") || word.equalsIgnoreCase("namiesto")) {
					wordEntry.negator = "gen";
				} else if(word.equalsIgnoreCase("nie")) {
					wordEntry.negator = "nie";
				} else {
					wordEntry.negator = strategy.detect(wordEntry);
				}
			}
		}
	}

	@Override
	public void detectNegators(List<SentenceNKE> sentences) {
		detectNegators(sentences, new NegativePrefixSlovakParadigmsStrategy());
	}

@Override
public void detectNegationScope(List<SentenceNKE> sentences) {
	Map<String, IScopeStrategy> strategyMap = new HashMap<String, IScopeStrategy>();
	strategyMap.put("gen", new ScopeStrategySlovakGen());
	strategyMap.put("pre", new ScopeStrategySlovakPred());
	strategyMap.put("atr", new ScopeStrategySlovakAttr());
	strategyMap.put("nie", new ScopeStrategySlovakNot());
	
	for(SentenceNKE sentence : sentences) {
		for(AbstractAnnotatedWord word : sentence.getWords()) {
			IScopeStrategy strategy = strategyMap.get(word.negator);
			if(strategy != null) {
				strategy.detectScope(sentence, word);
			}
		}
	}
}
}
