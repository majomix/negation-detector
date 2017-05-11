package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;

public class NegationDetectorFacade {

	public List<Document> documents;
	public List<String> unparsable;
	
	private List<Document> loadAnyCorpus(AbstractCorpusReader reader) {
		documents = reader.createCorpus();
		unparsable = reader.getUnparsable();
		
		return documents;
	}
	
	public List<Document> loadCorpus(String path) {
		return loadAnyCorpus(new CorpusReaderFileSystem(new File(path)));
	}
	
	public List<Document> loadStructuredCorpus(String path) {
		return loadAnyCorpus(new CorpusReaderXML(new File(path)));
	}
	
	public void saveStructuredCorpus(List<Document> documents, String path) {
		CorpusWriterXML writer = new CorpusWriterXML(new File(path));
		writer.saveCorpus(documents);
	}
	
	public void saveStructuredCorpus(String path) {
		saveStructuredCorpus(documents, path);
	}
	
	public Document markDocument(String text) {
		LanguageDetector detector;
		try {
			detector = new OptimaizeLangDetector().loadModels();
		} catch (IOException e1) {
			return null;
		}
		
		LanguageResult result = detector.detect(text);
		
		return markAnyDocument(text, result.getLanguage());
	}
	
	private Document markAnyDocument(String language, String text) {
		return new Document(text, language, "implicit");
	}
	
	public Document markSlovakDocument(String text) {
		return markAnyDocument(text, "sk");
	}

	public Document markEnglishDocument(String text) {
		return markAnyDocument(text, "en");
	}
	
}
