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

public class FileSystemCorpusReader extends AbstractCorpusReader {
	private File startPath;
	private List<String> fileList;
	
	public FileSystemCorpusReader(File startPath) {
		this.startPath = startPath;
		this.unparsable = new ArrayList<String>();
		
		fileList = new ArrayList<String>();
	}
	
	private void searchInDirectory(File currentFile) {
		if(currentFile == null) return;

		if(currentFile.isDirectory()) {
			for(File file : currentFile.listFiles()) {
				searchInDirectory(file);
			}
		} else if(currentFile.isFile()) {
			String finalPath;
			try {
				finalPath = currentFile.getCanonicalPath();
				fileList.add(finalPath);
			} catch (IOException e) {
				unparsable.add(currentFile.getPath());
			}
		}
	}

	@Override
	public List<Document> createCorpus() {
		List<Document> corpus = new ArrayList<Document>();
		LanguageDetector detector;
		Tika extractor = new Tika();
		
		searchInDirectory(startPath);
		
		try {
			detector = new OptimaizeLangDetector().loadModels();
		} catch (IOException e1) {
			e1.printStackTrace();
			return corpus;
		}
		
		for(String filename : fileList) {
			File file = new File(filename);
			String content;
			
			try {
				content = extractor.parseToString(file);
				LanguageResult result = detector.detect(content);
				
				Document document = new Document(content, filename, result.getLanguage());
				corpus.add(document);
			} catch (IOException|TikaException e) {
				unparsable.add(filename);
			}
		}

		return corpus;
	}
}
