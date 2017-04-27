package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class WordDictionaryLoaderParadigms {
	private static class WordDictionaryLoader {
		private static final Set<ParadigmEntry> set;
		static {
			set = new HashSet<ParadigmEntry>();
			File morpho = new File("F://Java//lib//ma-2015-02-05.txt");
			
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(morpho), "UTF-8"))) {
				String str;

				while ((str = reader.readLine()) != null) {
				    String[] parts = str.split("\t");
				    set.add(new ParadigmEntry(parts[0], StringUtils.substring(parts[2], 0, 1)));
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
	
	public static Set<ParadigmEntry> getInstance() {
		return WordDictionaryLoader.set;
	}
}
