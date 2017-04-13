package fiit.nlp.NegatedKeywordsExtractor;

import java.awt.EventQueue;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.annolab.tt4j.TreeTaggerException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;

import com.beust.jcommander.internal.Console;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoNLLOutputter;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import fiit.nlp.NegatedKeywordsExtractor.model.AbstractAnnotatedWord;
import fiit.nlp.NegatedKeywordsExtractor.model.AbstractCorpusReader;
import fiit.nlp.NegatedKeywordsExtractor.model.Document;
import fiit.nlp.NegatedKeywordsExtractor.model.ExtractorCore;
import fiit.nlp.NegatedKeywordsExtractor.model.FileSystemCorpusReader;
import fiit.nlp.NegatedKeywordsExtractor.model.SentenceNKE;
import fiit.nlp.NegatedKeywordsExtractor.view.MainWindow;
import is2.data.SentenceData09;
import is2.parser.Parser;
import lib.Sentence;
import lib.SentenceParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, TikaException, TreeTaggerException
    {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExtractorCore model = new ExtractorCore();
					//MainWindow window = new MainWindow();
					//window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
//    	  List<CoreMap> sentences = document.get(SentencesAnnotation.class);
//		  
//    	  for(CoreMap sentence: sentences) {
//    		  // traversing the words in the current sentence
//    		  // a CoreLabel is a CoreMap with additional token-specific methods
//    		  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
//    		    // this is the text of the token
//    		    String word = token.get(TextAnnotation.class);
//    		    // this is the POS tag of the token
//    		    String pos = token.get(PartOfSpeechAnnotation.class);
//    		    // this is the NER label of the token
//    		    String ne = token.get(NamedEntityTagAnnotation.class);
//    		  }
//
//    		  // this is the parse tree of the current sentence
//    		  Tree tree = sentence.get(TreeAnnotation.class);
//
//    		  // this is the Stanford dependency graph of the current sentence
//    		  SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//    		}
//
//    		// This is the coreference link graph
//    		// Each chain stores a set of mentions that link to each other,
//    		// along with a method for getting the most representative mention
//    		// Both sentence and token offsets start at 1!
//    		Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
//    		
//    		Sentence sentence = new Sentence();
//    		String text2 = "Implementácia prebehla bez riadneho návrhu.";
//    		sentence.text = text2; //"Žijeme v dobe, kedy informatizácia vstupuje do každého segmentu našej spoločnosti. Výnimkou nie je ani cirkev. Snaží sa prezentovať na internete, byť dostupnou pre každého bežného človeka. Internet a moderné technológie, vstupujú aj do končín, ktoré sú pri zavádzaní nových vecí opatrné a striedme.";
//    		sentence.parserType = "betterPreds";
//    		SentenceParser parser = new SentenceParser();
//    		SentenceData09[] parsedSentences = parser.parse(sentence);
//    		
//			StringBuilder sb = new StringBuilder();
//			
//			for (SentenceData09 parsedSentence : parsedSentences) {
//				sb.append(parsedSentence.toString());
//			}
//			System.out.println(sb);
    }

}
