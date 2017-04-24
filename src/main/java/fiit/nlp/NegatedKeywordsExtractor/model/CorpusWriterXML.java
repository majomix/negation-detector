package fiit.nlp.NegatedKeywordsExtractor.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class CorpusWriterXML {

	private File workingDirectory;
	
	public CorpusWriterXML(File directory) {
		if(!directory.exists()) {
			directory.mkdirs();
		}
		workingDirectory = directory;
	}
	
	public void saveCorpus(List<Document> documents) {
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		
		for(int i = 0; i < documents.size(); i++) {
			try {
				Document document = documents.get(i);
				File file = new File(workingDirectory.getCanonicalPath() + "/" + createFinalName(document, i));
				xmlOutput.output(buildXML(document), new FileWriter(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String createFinalName(Document document, int i) {
		
		String filename;
		if(document.getFilename() == null) {
			filename = "document" + (i + 1);
		} else {
			filename = new File(document.getFilename()).getName();
		}
		filename += ".xml";
		
		return filename;
	}

	public org.jdom.Document buildXML(Document document) {
		Element root = new Element("document");
		org.jdom.Document xmlDocument = new org.jdom.Document(root);
		xmlDocument.setRootElement(root);
		
		for(SentenceNKE sentence : document.getSentences()) {
			Element sentenceXML = new Element("sentence");
			
			sentenceXML.setAttribute("text", sentence.getText());
			
//			Element conllXML = new Element("conll");
//			conllXML.setText(sentence.toString());
//			sentenceXML.addContent(conllXML);
			
			for(AbstractAnnotatedWord word : sentence.getWords()) {
				Element wordXML = new Element("word");
				wordXML.setAttribute("id", "w" + word.order);
				
				if(!("".equals(word.negator))) {
					wordXML.setAttribute("negator", word.negator);
				}
				
				String scopes = createListOfScopes(word.negationTargetOfNode);
				if(!("".equals(scopes))) {
					wordXML.setAttribute("scope", scopes);
				}
				
				wordXML.setAttribute("lemma", word.lemma.split("\\|")[0]);
				wordXML.setAttribute("pos", word.partOfSpeechFeaturesLine);
				
				wordXML.setText(word.word);
				
				sentenceXML.addContent(wordXML);
			}
			
			root.addContent(sentenceXML);
		}
		
		return xmlDocument;
	}
	
	public String createListOfScopes(List<Integer> scopes) {
		Collections.sort(scopes);
		StringBuilder stringBuilder = new StringBuilder();
		Iterator<Integer> iterator = scopes.iterator();
		
		while(iterator.hasNext()) {
			stringBuilder.append("w" + iterator.next());
			if(iterator.hasNext()) {
				stringBuilder.append(",");
			}
		}
		
		return stringBuilder.toString();
	}
}
