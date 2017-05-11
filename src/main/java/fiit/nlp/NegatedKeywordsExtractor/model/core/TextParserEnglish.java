package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoNLLOutputter;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class TextParserEnglish implements ITextParser {

	@Override
	public void parse(String text, List<SentenceNKE> sentences) {
		// Annotation document = new Annotation("Hello world! I love this place. Peter denies gravity. Robert does not like beer.");
		Annotation document = new Annotation(text);
		StanfordCoreNLP parser = ParserLoaderStanford.getInstance();
	  	parser.annotate(document);
	  	
	  	ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 
	  	try {
	  		CoNLLOutputter.conllPrint(document, outputStream, parser);
	  		String conllOutput = new String(outputStream.toByteArray()) + "$";
	  		
	  		Map<Integer, List<AbstractAnnotatedWord>> listOfWords = new HashMap<Integer, List<AbstractAnnotatedWord>>();
	  		StringBuilder builder = new StringBuilder();
	  		
	  		for(String token : conllOutput.split("\\r?\\n")) {
	  			if("".equals(token)) {
	  				SentenceNKE sentence = new SentenceNKE(builder.toString(), listOfWords);
	  				builder = new StringBuilder();
	  				listOfWords = new HashMap<Integer, List<AbstractAnnotatedWord>>();
	  				sentences.add(sentence);
	  			} else if("$".equals(token)) {
	  				break;
	  			} else {
		  			AbstractAnnotatedWord word = new AnnotatedWordEnglish(token);
		  			builder.append(token + "\n");

				    List<AbstractAnnotatedWord> currentValue = listOfWords.get(word.dependsOn);
				    if (currentValue == null) {
				        currentValue = new ArrayList<AbstractAnnotatedWord>();
				        listOfWords.put(word.dependsOn, currentValue);
				    }
				    currentValue.add(word);
	  			}
	  		}
	  	} catch (IOException e) {
	  		e.printStackTrace();
		}
	}

	@Override
	public void detectNegators(List<SentenceNKE> sentences, INegativePrefixStrategy strategy) {
		if(strategy == null) {
			detectNegators(sentences);
			return;
		}
		
		HashMap<String, String> genitiveMultiword = new HashMap<String,String>();
		genitiveMultiword.put("without", null);
		genitiveMultiword.put("than", "rather");
		genitiveMultiword.put("of", "instead");
		genitiveMultiword.put("from", "apart");
		
		for(SentenceNKE sentence : sentences) {
			for(AbstractAnnotatedWord wordEntry : sentence.getWords()) {
				if(genitiveMultiword.containsKey(wordEntry.word.toLowerCase())) {
					inspectGenitiveNegatorCandidates(wordEntry, genitiveMultiword.get(wordEntry.word.toLowerCase()), sentence.getTree());
				} else if(wordEntry.word.equalsIgnoreCase("not")) {
					wordEntry.negator = "not";
				} else if(wordEntry.word.equalsIgnoreCase("no") || wordEntry.word.equalsIgnoreCase("none")) {
					wordEntry.negator = "non";
				} else if(wordEntry.word.equalsIgnoreCase("neither") || wordEntry.word.equalsIgnoreCase("nor")) {
					wordEntry.negator = "cpd";
				} else if (wordEntry.lemma.equalsIgnoreCase("lack")) {
					wordEntry.negator = "pre";
				} else if(wordEntry.word.endsWith("less")) {
					wordEntry.negator = "att";
				} else if(wordEntry.word.endsWith("lessly")) {
					wordEntry.negator = "adv";
				} else if(wordEntry.word.endsWith("lessness")) {
					wordEntry.negator = "sbs";
				} else {
					wordEntry.negator = strategy.detect(wordEntry);
				}
			}
		}
	}
	
	private void inspectGenitiveNegatorCandidates(AbstractAnnotatedWord negator, String compoundNegator, DefaultTreeForTreeLayout<AbstractAnnotatedWord> tree) {
		// single word
		if(compoundNegator == null) {
			negator.negator = "gen";
		}
		// possible multiword negator
		else {
			if(compoundNegator.equalsIgnoreCase(tree.getParent(negator).word)) {
				negator.negator = "gnc";
				tree.getParent(negator).negator = "gen";
			}
		}
	}

	@Override
	public void detectNegators(List<SentenceNKE> sentences) {
		detectNegators(sentences, new NegativePrefixEnglishStrategy());
	}

	@Override
	public void detectNegationScope(List<SentenceNKE> sentences) {
		Map<String, IScopeStrategy> strategyMap = new HashMap<String, IScopeStrategy>();
		strategyMap.put("gen", new ScopeStrategyEnglishRightSibling());
		strategyMap.put("pre", new ScopeStrategyEnglishPre());
		strategyMap.put("not", new ScopeStrategyEnglishRightSibling());
		strategyMap.put("non", new ScopeStrategyEnglishRightSibling());
		strategyMap.put("cpd", new ScopeStrategyEnglishRightSibling());
		
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
