package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fiit.nlp.NegatedKeywordsExtractor.model.EvaluatorCorpusNegationScope;
import fiit.nlp.NegatedKeywordsExtractor.model.EvaluatorCorpusNegationStatistics;
import fiit.nlp.NegatedKeywordsExtractor.model.EvaluatorCorpusNegators;
import fiit.nlp.NegatedKeywordsExtractor.model.EvaluatorStatistics;
import fiit.nlp.NegatedKeywordsExtractor.model.ScoreCalculator;
import fiit.nlp.Synpar.SentenceParser;

public class ExtractorCore {

	private List<Document> documents;
	private List<String> unparsable;
	String slovakPath = "F://FIIT//Diplomová práca//Predošlé práce//Matej Kvitkovič//data//doc//technicke_recenzie";
	String englishPath = "F://Java//Dataset//english";
	String devSlovakPath = "F://Java//Dataset//slovak";
	String devEnglishPath = "F://Java//Dataset//english2";
	String testPath = "F://Java//Dataset//test";
	String maPath = "F://Java//Dataset//ma";
	String sportPath = "F://Java//Dataset//clanky//sport";
	String xmlPath = "F://Java//Dataset//xml";
	
	public List<String> loadCorpus(String path) {
		AbstractCorpusReader reader;
		
		HashMap<String, List<Document>> corpus = new HashMap<String, List<Document>>();
		
		reader = new CorpusReaderXML(new File(xmlPath + "//beletria"));
		List<Document> fictionDocuments = reader.createCorpus();
		corpus.put("Beletria", fictionDocuments);
		
		reader = new CorpusReaderXML(new File(xmlPath + "//recenzie"));
		List<Document> photoDocuments = reader.createCorpus();
		corpus.put("Fotoaparaty", photoDocuments);

		reader = new CorpusReaderXML(new File(xmlPath + "//sport"));
		List<Document> sportDocuments = reader.createCorpus();
		corpus.put("Sport", sportDocuments);
		
		reader = new CorpusReaderXML(new File(xmlPath + "//gaborik"));
		List<Document> gaborikDocuments = reader.createCorpus();
		corpus.put("SNK", gaborikDocuments);

		reader = new CorpusReaderXML(new File(xmlPath + "//vlastne"));
		List<Document> ownDocuments = reader.createCorpus();
		corpus.put("Vlastne", ownDocuments);
		
		EvaluatorStatistics evaluatorStatistics = new EvaluatorStatistics();
		EvaluatorCorpusNegators evaluatorCorpusNegators = new EvaluatorCorpusNegators();
		EvaluatorCorpusNegationStatistics evaluatorNegationStatistics = new EvaluatorCorpusNegationStatistics();
		EvaluatorCorpusNegationScope evaluatorScope = new EvaluatorCorpusNegationScope();
		
		for(Map.Entry<String, List<Document>> entry : corpus.entrySet()) {
			evaluatorStatistics.evaluate(entry.getKey(), entry.getValue());
			evaluatorCorpusNegators.evaluate(entry.getKey(), entry.getValue());
			evaluatorNegationStatistics.evaluate(entry.getKey(), entry.getValue());
			evaluatorScope.evaluate(entry.getKey(), entry.getValue());
			
		}
		
		evaluatorScope.printAllResults();
		
		//AbstractCorpusReader reader = new CorpusReaderFileSystem(new File(path));
		//AbstractCorpusReader reader = new CorpusReaderXML(new File(path));
		//documents = reader.createCorpus();
		
		
		
//		CorpusWriterXML writer = new CorpusWriterXML(new File("F://Java//Dataset//xml//vlastne"));
//		writer.saveCorpus(documents);
//		
//		int totalWords = 0;
//		int matchedNegators = 0;
//		int notMatchedNegators = 0;
//		
//		for(Document document : documents) {
//			for(SentenceNKE sentence : document.getSentences()) {
//				for(AbstractAnnotatedWord word : sentence.getWords()) {
//					totalWords++;
//					if(word.negator.equals(word.expectedNegator)) {
//						matchedNegators++;
//					} else {
//						notMatchedNegators++;
//						System.out.println(word.word + ", určený typ: " + word.negator + ", očakávaný typ: " + word.expectedNegator);
//					}
//				}
//			}
//		}
    	
    	//return reader.getUnparsable();
		return null;
	}
	
	public ExtractorCore() {
    	//unparsable = loadCorpus("F://Java//Dataset//clanky//vlastne");
    	
		ScoreCalculator calculator = new ScoreCalculator(
				3039 + 85 + 12 + 12 + 73,
				6991 + 1209 + 865 + 373 + 1539,
				584 + 2 + 6 + 9 + 2,
				351 + 0 + 18 + 8 + 9
				);
		System.out.println(calculator);
		
//    	EvaluatorNegativePrefixSlovakParadigsm a = new EvaluatorNegativePrefixSlovakParadigsm();
//    	a.PrepareManualTest();

    	//unparsable = loadCor pus("F://Java//Dataset//xml");
	}
}
