package fiit.nlp.NegatedKeywordsExtractor.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lib.SentenceParser;

public class ExtractorCore {

	private List<Document> documents;
	private List<String> unparsable;
	
	public List<String> loadCorpus(String path)
	{
		AbstractCorpusReader reader = new CorpusReaderFileSystem(new File(path));
		//AbstractCorpusReader reader = new CorpusReaderXML(new File(path));
		documents = reader.createCorpus();
		
		CorpusWriterXML writer = new CorpusWriterXML(new File("F://Java//Dataset//new"));
		writer.saveCorpus(documents);
		
		int totalWords = 0;
		int matchedNegators = 0;
		int notMatchedNegators = 0;
		
		for(Document document : documents) {
			for(SentenceNKE sentence : document.getSentences()) {
				for(AbstractAnnotatedWord word : sentence.getWords()) {
					totalWords++;
					if(word.negator.equals(word.expectedNegator)) {
						matchedNegators++;
					} else {
						notMatchedNegators++;
						System.out.println(word.word + ", určený typ: " + word.negator + ", očakávaný typ: " + word.expectedNegator);
					}
				}
			}
		}
    	
    	return reader.getUnparsable();
	}
	
	public ExtractorCore() {
    	String slovakPath = "F://FIIT//Diplomová práca//Predošlé práce//Matej Kvitkovič//data//doc//technicke_recenzie";
    	String englishPath = "F://Java//Dataset//english";
    	String devSlovakPath = "F://Java//Dataset//slovak";
    	String devEnglishPath = "F://Java//Dataset//english2";
    	String testPath = "F://Java//Dataset//test";
    	unparsable = loadCorpus(devSlovakPath);
    	//unparsable = loadCorpus("F://Java//Dataset//xml");
	}
}
