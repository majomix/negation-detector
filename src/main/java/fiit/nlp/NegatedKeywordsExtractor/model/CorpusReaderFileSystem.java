package fiit.nlp.NegatedKeywordsExtractor.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;

public class CorpusReaderFileSystem extends AbstractCorpusReader {

	public CorpusReaderFileSystem(File startPath) {
		super(startPath);
	}
	
	@Override
	public List<Document> createCorpus() {
		List<Document> corpus = new ArrayList<Document>();
		Tika extractor = new Tika();
		
		searchInDirectory(startPath);
		
		for(String filename : fileList) {
			File file = new File(filename);
			String content;
			
			try {
				content = extractor.parseToString(file);
				LanguageResult result = detector.detect(content);
				
				Document document = new Document(content, result.getLanguage(), filename);
				corpus.add(document);
			} catch (IOException|TikaException e) {
				unparsable.add(filename);
			}
		}

		return corpus;
	}
}
