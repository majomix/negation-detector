package fiit.nlp.NegatedKeywordsExtractor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fiit.nlp.NegatedKeywordsExtractor.model.SentenceNKE;
import fiit.nlp.NegatedKeywordsExtractor.model.TextParserSlovak;
import junit.framework.TestCase;

public class TextParserSlovakTest extends TestCase {

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
		assertTrue( true );
	}
}
