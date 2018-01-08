package fiit.nlp.NegatedKeywordsExtractor;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import fiit.nlp.NegatedKeywordsExtractor.model.core.AbstractAnnotatedWord;
import fiit.nlp.NegatedKeywordsExtractor.model.core.Document;
import fiit.nlp.NegatedKeywordsExtractor.model.core.NegationDetectorFacade;
import fiit.nlp.NegatedKeywordsExtractor.model.core.SentenceNKE;

public class App 
{
    public static void main( String[] args )
    {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NegationDetectorFacade model = new NegationDetectorFacade();
					List<Document> documents = model.loadCorpus("C://d");
					List<ArrayList<AbstractAnnotatedWord>> allWords = new ArrayList<ArrayList<AbstractAnnotatedWord>>();
					
					for(Document document : documents) {
						ArrayList<AbstractAnnotatedWord> documentWords = new ArrayList<AbstractAnnotatedWord>();
						
						for(SentenceNKE sentence : document.getSentences()) {
							documentWords.addAll(sentence.getWords());
						}
						
						allWords.add(documentWords);
					}
					
					TfIdfCalculator calculator = new TfIdfCalculator();
					
					for(ArrayList<AbstractAnnotatedWord> words : allWords) {
						HashMap<String, Double> keywordsWithoutNegation = new HashMap<String, Double>();
						HashMap<String, Double> keywordsWithNegation = new HashMap<String, Double>();
						
						for(AbstractAnnotatedWord word : words) {
							keywordsWithoutNegation.put(word.lemma, calculator.tfIdf(words, allWords, word.lemma, false));
							keywordsWithNegation.put(word.lemma, calculator.tfIdf(words, allWords, word.lemma, true));
						}
						
						ArrayList<Entry<String, Double>> listKeywordsWithoutNegation = new ArrayList<Entry<String, Double>>(keywordsWithoutNegation.entrySet());
						ArrayList<Entry<String, Double>> listKeywordsWithNegation = new ArrayList<Entry<String, Double>>(keywordsWithNegation.entrySet());
						
						Collections.sort(listKeywordsWithoutNegation, new EntrySetComparator());
						Collections.sort(listKeywordsWithNegation, new EntrySetComparator());
						
						
						System.out.println("!!! DOKUMENT !!!");
						for(int i = 0; i < 30; i++) {
							System.out.println(i + ". " + listKeywordsWithoutNegation.get(i).getKey() + " - " + listKeywordsWithoutNegation.get(i).getValue());
							System.out.println(i + ". " + listKeywordsWithNegation.get(i).getKey() + " - " + listKeywordsWithNegation.get(i).getValue());
						}
					}
					
					//MainWindow window = new MainWindow();
					//window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

}
