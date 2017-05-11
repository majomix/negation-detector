package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class WordDictionaryLoaderParadigms {
	private static class WordDictionaryLoader {
		private static final Set<WordEntryParadigm> set;
		static {
			set = new HashSet<WordEntryParadigm>();
			File morpho = new File(NegationDetectorConfiguration.getProperty("morphologicalDatabase"));
			
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(morpho), "UTF-8"))) {
				String str;

				while ((str = reader.readLine()) != null) {
				    String[] parts = str.split("\t");
				    set.add(new WordEntryParadigm(parts[0], StringUtils.substring(parts[2], 0, 1)));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private WordDictionaryLoaderParadigms() {
		if(WordDictionaryLoader.set != null) {
			throw new IllegalStateException("Already instantiated");
		}
	}
	
	public static Set<WordEntryParadigm> getInstance() {
		return WordDictionaryLoader.set;
	}
}
