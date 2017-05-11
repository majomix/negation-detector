package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class WordDictionaryLoaderSentiWordNet {
	private static class WordDictionaryLoader {
		private static final Map<String, WordEntrySentiWordNet> set;
		static {
			set = new HashMap<String, WordEntrySentiWordNet>();
			File morpho = new File(NegationDetectorConfiguration.getProperty("sentiWordNet"));
			
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(morpho), "UTF-8"))) {
				String str;

				while ((str = reader.readLine()) != null) {
				    if(!(str.substring(0, 1).equals("#"))) {
						String[] parts = str.split("\t");
						String lemma = parts[4].split("#")[0];
					    set.put(lemma, new WordEntrySentiWordNet(parts[0], parts[1], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), lemma));
				    }
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private WordDictionaryLoaderSentiWordNet() {
		if(WordDictionaryLoader.set != null) {
			throw new IllegalStateException("Already instantiated");
		}
	}
	
	public static Map<String, WordEntrySentiWordNet> getInstance() {
		return WordDictionaryLoader.set;
	}
}
