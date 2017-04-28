package fiit.nlp.NegatedKeywordsExtractor.model.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.language.detect.LanguageResult;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;

public class CorpusReaderBioScope extends AbstractCorpusReader {

	protected CorpusReaderBioScope(File startPath) {
		super(startPath);
	}

	@Override
	public List<Document> createCorpus() {
		List<Document> corpus = new ArrayList<Document>();
		
		searchInDirectory(startPath);
		
		SAXBuilder builder = new SAXBuilder();
		ITextParser parser = new TextParserEnglish();
		List<SentenceNKE> sentences;
		
		for(String filename : fileList) {
			sentences = new ArrayList<SentenceNKE>();
			org.jdom.Document documentXML;
			try {
				documentXML = (org.jdom.Document) builder.build(new File(filename));
				
				Element rootNode = documentXML.getRootElement();
				List documentsXML = ((Element) rootNode.getChildren("DocumentSet").get(0)).getChildren("Document");
				
				for (int i = 0; i < documentsXML.size(); i++) {
					List documentPartsXML = ((Element) documentsXML.get(i)).getChildren("DocumentPart");
					for(int j = 0; j < documentPartsXML.size(); j++) {
						Element part = (Element) documentPartsXML.get(j);
						String partType = part.getAttributeValue("type");
						if(partType.equals("Text") || partType.equals("AbstractText")) {
							List sentencesXML = part.getChildren("sentence");
							for(int y = 0; y < sentencesXML.size(); y++) {
								CompositeScope scope = new CompositeScope();
								scope.addContent((Element) sentencesXML.get(y));
								
								List<SentenceNKE> parsedSentences = new ArrayList<SentenceNKE>();
								parser.parse(scope.getText(), parsedSentences);
								SentenceNKE parsedSentence = parsedSentences.get(0);
								
								markExpectedNegations(parsedSentence, scope);
							}
						}
					}

					
//					stringBuilder.append(sentenceText);
				}
			} catch (JDOMException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private void markExpectedNegations(SentenceNKE parsedSentence, ICompositeScope scope) {
		
		List<ICompositeScope> negatedContent = scope.getNegatedScope();
		
		for(int i = 0; i < negatedContent.size(); i++) {
			int minStart = -1, maxEnd = -1;
			List<AbstractAnnotatedWord> words = parsedSentence.getWords();
			
			int maxConsecutive = 0;
			for(int start = 0; start < words.size(); start++) {
				int consecutive = 0;
				for(int end = start; end < words.size(); end++) {
					if(negatedContent.get(i).getText().contains(words.get(end).word)) {
						consecutive++;
						if(consecutive >= maxConsecutive) {
							minStart = start;
							maxEnd = end;
							maxConsecutive = consecutive;
						}
					} else {
						consecutive = 0;
					}
				}
			}
			
			String negatorValue = "";
			for(ICompositeScope possibleNegator : negatedContent.get(i).getContent()) {
				if(possibleNegator instanceof CueScope) {
					negatorValue = possibleNegator.getText();
				}
			}
			
			AbstractAnnotatedWord negator = null;
			for(int j = minStart; j <= maxEnd; j++) {
				AbstractAnnotatedWord word = parsedSentence.getWord(j);
				
				if(word.word.equals(negatorValue)) {
					negator = word;
					negator.expectedNegator = "yes";
				}
			}
			
			if(negator != null) {
				for(int j = minStart; j <= maxEnd; j++) {
					AbstractAnnotatedWord word = parsedSentence.getWord(j);
					
					if(word != negator) {
						word.expectedNegationTargetOfNode.add(negator.order);
					}
				}
			}
		}
	}

	public interface ICompositeScope {
		public String getText();
		public void addContent(Object element);
		public List<ICompositeScope> getContent();
		public List<ICompositeScope> getNegatedScope();
	}
	
	public class CompositeScope implements ICompositeScope {
		List<ICompositeScope> scopes;
		
		public CompositeScope() {
			scopes = new ArrayList<ICompositeScope>();
		}
		
		@Override
		public String getText() {
			StringBuilder stringBuilder = new StringBuilder();
			
			for(ICompositeScope scope : scopes) {
				stringBuilder.append(scope.getText());
			}
			
			return stringBuilder.toString();
		}

		@Override
		public void addContent(Object element) {
			List content = ((Element) element).getContent();
			
			for(int i = 0; i < content.size(); i++) {
				Object child = content.get(i);

				ICompositeScope scope = null;
				
				if(child instanceof Text) {
					scope = new TextScope();
				} else if(child instanceof Element) {
					Element elementChild = (Element)child;
					if(elementChild.getName().equals("cue")) {
						scope = new CueScope();
					} else if(elementChild.getName().equals("xcope")) {
						scope = new CompositeScope();
					}
				}
				
				if(scope != null) {
					scope.addContent(child);
					scopes.add(scope);
				}
			}
		}

		@Override
		public List<ICompositeScope> getNegatedScope() {
			List<ICompositeScope> allScopes = new ArrayList<ICompositeScope>();
			
			boolean hasNegation = false;
			for(ICompositeScope scope : scopes) {
				if(scope instanceof CueScope) {
					if(((CueScope)scope).getType().equals("negation")) {
						hasNegation = true;
					}
				}
			}
			
			if(hasNegation) {
				allScopes.add(this);
			}
			
			for(ICompositeScope scope : scopes) {
				if(scope instanceof CompositeScope) {
					allScopes.addAll(scope.getNegatedScope());
				}
			}
			
			return allScopes;
		}

		@Override
		public List<ICompositeScope> getContent() {
//			List<ICompositeScope> allScopes = new ArrayList<ICompositeScope>();
//			
//			for(ICompositeScope scope : scopes) {
//				List<ICompositeScope> internalScopes = scope.getContent();
//				if(internalScopes != null) {
//					allScopes.addAll(internalScopes);
//				}
//			}
//			return allScopes;
			return scopes;
		}
	}
	
	public class TextScope implements ICompositeScope {
		String text;
		
		@Override
		public String getText() {
			return text;
		}

		@Override
		public void addContent(Object text) {
			this.text = ((Text)text).getText();
		}

		@Override
		public List<ICompositeScope> getNegatedScope() {
			return null;
		}
		
		@Override
		public List<ICompositeScope> getContent() {
			List<ICompositeScope> list = new ArrayList<ICompositeScope>();
			list.add(this);
			return list;
		}
	}
	
	public class CueScope implements ICompositeScope {
		String text;
		String type;
		
		@Override
		public String getText() {
			return text;
		}

		@Override
		public void addContent(Object cue) {
			this.text = ((Element)cue).getText();
			this.type = ((Element)cue).getAttributeValue("type");
		}
		
		public String getType() {
			return type;
		}

		@Override
		public List<ICompositeScope> getNegatedScope() {
			return null;
		}
		

		@Override
		public List<ICompositeScope> getContent() {
			List<ICompositeScope> list = new ArrayList<ICompositeScope>();
			list.add(this);
			return list;
		}
	}

}
