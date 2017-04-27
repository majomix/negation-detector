package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;

public abstract class AbstractCorpusReader {
	protected List<String> unparsable;
	protected File startPath;
	protected List<String> fileList;
	protected LanguageDetector detector;

	public abstract List<Document> createCorpus();
	
	public List<String> getUnparsable() {
		return unparsable;
	}
	
	protected AbstractCorpusReader(File startPath) {
		this.startPath = startPath;
		this.unparsable = new ArrayList<String>();
		
		try {
			detector = new OptimaizeLangDetector().loadModels();
		} catch (IOException e1) {
			detector = null;
		}
		
		fileList = new ArrayList<String>();
	}
	
	protected void searchInDirectory(File currentFile) {
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
}
