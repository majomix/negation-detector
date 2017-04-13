package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.Properties;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class ParserLoaderStanford {
	private static class ParserLoader {
		private static final StanfordCoreNLP parser;
		static {
			Properties props = new Properties();
			props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
			props.setProperty("ner.useSUTime", "0");
			parser = new StanfordCoreNLP(props);
		}
	}
	
	private ParserLoaderStanford() {
		if(ParserLoader.parser != null) {
			throw new IllegalStateException("Already instantiated");
		}
	}
	
	public static StanfordCoreNLP getInstance() {
		return ParserLoader.parser;
	}
}
