package fiit.nlp.NegatedKeywordsExtractor.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class EvaluatorNegativePrefixSlovakParadigms {
	public void PrepareManualTest() {
    	Set<ParadigmEntry> set = WordDictionaryLoaderParadigms.getInstance();
    	ArrayList<AnnotatedWordSlovak> words = new ArrayList<AnnotatedWordSlovak>();
    	NegativePrefixSlovakParadigmsStrategy strategy = new NegativePrefixSlovakParadigmsStrategy();
    	
    	for(ParadigmEntry entry : set) {
    		AnnotatedWordSlovak word = new AnnotatedWordSlovak(entry.lemma, entry.pos);
    		word.negator = strategy.detect(word);
    		words.add(word);
    	}
    	
    	Collections.sort(words, new Comparator<AbstractAnnotatedWord>() {
			@Override
			public int compare(AbstractAnnotatedWord o1, AbstractAnnotatedWord o2) {
				return o1.lemma.toLowerCase().compareTo(o2.lemma.toLowerCase());
			}
		});
    	
//    	try(BufferedWriter writer = new BufferedWriter(new FileWriter( "F://Java//Dataset//ma//ma_unique.txt")))
//    	{
//        	for(AnnotatedWordSlovak checkedWord : words) {
//        		writer.write(checkedWord.lemma + " " + checkedWord.partOfSpeechTag + "\n");
//        	}
//    	} catch (IOException e1) {
//
//		}
    	
    	try(BufferedWriter writer = new BufferedWriter(new FileWriter( "F://Java//Dataset//ma//test.txt")))
    	{
        	for(AnnotatedWordSlovak checkedWord : words) {
        		String word = checkedWord.lemma.toLowerCase();
        		String[] result = word.split("(?<=^(" + String.join("|", strategy.getPrefixes()) +"))");
        		
        		if(result.length == 1) {
        			continue;
        		}
        		
        		writer.write(word + " " + checkedWord.partOfSpeechTag + " ");
        		
        		if("".equals(checkedWord.negator)) {
        			writer.write("NO FN\n");
        		} else {
        			writer.write("YES(" + checkedWord.negator + ") TP\n");
        		}
        	}
    	} catch (IOException e) {
		}
	}
}
