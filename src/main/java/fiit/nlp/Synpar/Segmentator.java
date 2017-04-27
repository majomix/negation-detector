package fiit.nlp.Synpar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.*;

public class Segmentator {

	static Segmentator instance;
	SentenceDetectorME sentenceDetector;
	
	private Segmentator() throws FileNotFoundException {
		InputStream modelIn = new FileInputStream("F:/Java/lib/en-sent.bin");
		
		try {
		  SentenceModel model = new SentenceModel(modelIn);
		  sentenceDetector = new SentenceDetectorME(model);
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}
	}
	
	public static Segmentator getInstance() throws FileNotFoundException {
		if (instance == null) {
			instance = new Segmentator();
			return instance;
		}
		else return instance;
	}
	
	public String[] getSentences(String inputText) {
		return sentenceDetector.sentDetect(inputText);
	}
	
}
