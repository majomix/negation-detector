package fiit.nlp.NegatedKeywordsExtractor.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

				    List<AbstractAnnotatedWord> currentValue = listOfWords.get(word.getDependsOn());
				    if (currentValue == null) {
				        currentValue = new ArrayList<AbstractAnnotatedWord>();
				        listOfWords.put(word.getDependsOn(), currentValue);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detectNegators(List<SentenceNKE> sentences) {
		// TODO Auto-generated method stub
		
	}
}
