package fiit.nlp.NegatedKeywordsExtractor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fiit.nlp.NegatedKeywordsExtractor.model.core.SentenceNKE;
import fiit.nlp.NegatedKeywordsExtractor.model.core.TextParserSlovak;

public class TextParserSlovakTest {

	private TextParserSlovak parser;
	
	@Before
	public void beforeEachTest() {
		parser = new TextParserSlovak();
	}
	
	@Test
	public void textParserSlovak_GenitivePreposition_TagAsNegator() {
		// arrange
		List<SentenceNKE> sentences = new ArrayList<SentenceNKE>();
		parser.parse("Bez ohľadu na to.", sentences);
		
		// act
		parser.detectNegators(sentences);
		
		// assert
		sentences.get(0).getWords()
		assertTrue( true );
	}
}
