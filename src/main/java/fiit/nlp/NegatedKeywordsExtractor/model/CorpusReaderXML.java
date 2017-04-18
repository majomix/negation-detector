package fiit.nlp.NegatedKeywordsExtractor.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.input.SAXBuilder;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;

public class CorpusReaderXML extends AbstractCorpusReader {

	protected CorpusReaderXML(File startPath) {
		super(startPath);
	}

	@Override
	public List<Document> createCorpus() {
		List<Document> corpus = new ArrayList<Document>();
		StringBuilder stringBuilder = new StringBuilder();
		
		searchInDirectory(startPath);
		
		SAXBuilder builder = new SAXBuilder();
		ITextParser parser = new TextParserSlovak();
		List<SentenceNKE> sentences = new ArrayList<SentenceNKE>();
		
		for(String filename : fileList) {
			org.jdom.Document documentXML;
			try {
				documentXML = (org.jdom.Document) builder.build(new File(filename));
				
				Element rootNode = documentXML.getRootElement();
				List list = rootNode.getChildren("sentence");
				
				for (int i = 0; i < list.size(); i++) {
					Element sentence = (Element) list.get(i);
					String sentenceText = sentence.getAttributeValue("text");
					stringBuilder.append(sentenceText);
					
					List<SentenceNKE> parsedSentences = new ArrayList<SentenceNKE>();
					parser.parse(sentenceText, parsedSentences);
					SentenceNKE parsedSentence = parsedSentences.get(0);

					List words = sentence.getChildren("word");
					
					for (int y = 0; y < words.size(); y++) {
						Element word = (Element) words.get(y);
						AbstractAnnotatedWord parsedWord = parsedSentence.getWord(y);

						String value = word.getText();
						if(parsedWord != null && value.equals(parsedWord.word)) {
							List attributes = word.getAttributes();
							for(int j = 0; j < attributes.size(); j++) {
								Attribute attr = (Attribute) attributes.get(j);
								if(attr.getName().equals("negator")) {
									parsedWord.expectedNegator = attr.getValue();
								} else if(attr.getName().equals("scope")) {
									for(String id : attr.getValue().split(",")) {
										parsedWord.expectedNegationTargetOfNode.add(Integer.parseInt(id.replaceAll("\\D+","")));
									}
								}
							}
						} else {
							System.out.println("Word '" + value + "' mismatch in sentence #" + i + " '" + sentenceText +  "'.");
						}
					}
					
					sentences.add(parsedSentence);
				}
			} catch (JDOMException | IOException e) {
				e.printStackTrace();
			}
			
			String rawText = stringBuilder.toString();
			LanguageResult result = detector.detect(rawText);
			
			Document document = new Document(rawText, sentences, result.getLanguage());
			corpus.add(document);

		}

		return corpus;
	}

}
