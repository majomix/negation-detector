package fiit.nlp.Synpar;

import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;

import opennlp.tools.tokenize.SimpleTokenizer;

import static java.util.Arrays.asList;
import java.io.IOException;
import java.util.LinkedList;
public class Tagger {
	TreeTaggerWrapper<String> tagger;
	SimpleTokenizer tokenizer;
	
	public Tagger() throws IOException {
		tokenizer = new SimpleTokenizer();

		final String taggerModel = "F:/Java/lib/slovak2-utf8.par";
		
		tagger = new TreeTaggerWrapper<String>();	

		tagger.setModel(taggerModel);
	}
	
	public Sentence tagSentence(Sentence sentence) throws IOException, TreeTaggerException {
		String[] tokenized = tokenizer.tokenize(sentence.text); 
		final LinkedList<SentenceToken> tokens = new LinkedList<SentenceToken>();
		
		tagger.setHandler(new TokenHandler<String>() {
			public void token(String form, String pos, String lemma) {
				SentenceToken token = new SentenceToken();
				token.form = form;
				token.pos = pos == "SENT" ? "Z" : pos;
				token.lemma = lemma;
				tokens.add(token);
			}
		});
		

		tagger.process(asList(tokenized));
		
		sentence.tokens = tokens;
		
		return sentence;
	}
}
