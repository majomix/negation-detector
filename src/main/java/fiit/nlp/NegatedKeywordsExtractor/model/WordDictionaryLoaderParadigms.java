package fiit.nlp.NegatedKeywordsExtractor.model;

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

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class WordDictionaryLoaderParadigms {
	private static class WordDictionaryLoader {
		private static final Set<String> set;
		static {
			set = new HashSet<String>();
			File morpho = new File("F://Java//lib//ma-2015-02-05.txt");
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(morpho), "UTF-8"));
				
				String str;

				while ((str = reader.readLine()) != null) {
				    set.add(str.split("\t")[0]);
				    System.out.println(str);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private WordDictionaryLoaderParadigms() {
		if(WordDictionaryLoader.set != null) {
			throw new IllegalStateException("Already instantiated");
		}
	}
	
	public static Set<String> getInstance() {
		return WordDictionaryLoader.set;
	}
}
