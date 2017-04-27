package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.IOException;

import fiit.nlp.Synpar.SentenceParser;

public final class ParserLoaderSynpar {
	private static class ParserLoader {
		private static final SentenceParser parser;
		static {
			try {
				parser = new SentenceParser();
			} catch (IOException e) {
				throw new ExceptionInInitializerError(e);
			}
		}
	}
	
	private ParserLoaderSynpar() {
		if(ParserLoader.parser != null) {
			throw new IllegalStateException("Already instantiated");
		}
	}
	
	public static SentenceParser getInstance() {
		return ParserLoader.parser;
	}
}
