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
    	//AbstractCorpusReader reader = new CorpusReaderFileSystem(new File(path));
		AbstractCorpusReader reader = new CorpusReaderXML(new File(path));
		documents = reader.createCorpus();
    	
    	return reader.getUnparsable();
	}
	
	public ExtractorCore() {
    	String slovakPath = "F://FIIT//Diplomová práca//Predošlé práce//Matej Kvitkovič//data//doc//technicke_recenzie";
    	String englishPath = "F://Java//Dataset//english";
    	String devSlovakPath = "F://Java//Dataset//slovak";
    	String devEnglishPath = "F://Java//Dataset//english2";
    	//unparsable = loadCorpus("F://Java//Dataset//test");
    	unparsable = loadCorpus("F://Java//Dataset//xml");
	}
}
