package fiit.nlp.NegatedKeywordsExtractor.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fiit.nlp.NegatedKeywordsExtractor.model.core.*;


public class EvaluatorRunAll {

	public void run() {
		String xmlPath = "F://Java//Dataset//xml";
		AbstractCorpusReader reader;
		HashMap<String, List<Document>> corpus = new HashMap<String, List<Document>>();
			
		reader = new CorpusReaderXML(new File(xmlPath + "//beletria"));
		List<Document> fictionDocuments = reader.createCorpus();
		corpus.put("Beletria", fictionDocuments);
		
		reader = new CorpusReaderXML(new File(xmlPath + "//recenzie"));
		List<Document> photoDocuments = reader.createCorpus();
		corpus.put("Fotoaparaty", photoDocuments);

		reader = new CorpusReaderXML(new File(xmlPath + "//sport"));
		List<Document> sportDocuments = reader.createCorpus();
		corpus.put("Sport", sportDocuments);
		
		reader = new CorpusReaderXML(new File(xmlPath + "//gaborik"));
		List<Document> gaborikDocuments = reader.createCorpus();
		corpus.put("SNK", gaborikDocuments);
		
		reader = new CorpusReaderXML(new File(xmlPath + "//tp"));
		List<Document> tpDocuments = reader.createCorpus();
		corpus.put("TP", tpDocuments);

		reader = new CorpusReaderXML(new File(xmlPath + "//vlastne"));
		List<Document> ownDocuments = reader.createCorpus();
		corpus.put("Vlastne", ownDocuments);

		reader = new CorpusReaderXML(new File(xmlPath + "//ladenie"));
		List<Document> debugDocuments = reader.createCorpus();
		corpus.put("Ladenie", debugDocuments);
			
			EvaluatorStatistics evaluatorStatistics = new EvaluatorStatistics();
			EvaluatorCorpusNegators evaluatorCorpusNegators = new EvaluatorCorpusNegators();
			EvaluatorCorpusNegationStatistics evaluatorNegationStatistics = new EvaluatorCorpusNegationStatistics();
			EvaluatorCorpusNegationScope evaluatorScope = new EvaluatorCorpusNegationScope();
			EvaluatorCorpusAllNegators evaluatorAllNegators = new EvaluatorCorpusAllNegators();
			
			for(Map.Entry<String, List<Document>> entry : corpus.entrySet()) {
				evaluatorStatistics.evaluate(entry.getKey(), entry.getValue());
				evaluatorCorpusNegators.evaluate(entry.getKey(), entry.getValue());
				evaluatorNegationStatistics.evaluate(entry.getKey(), entry.getValue());
				evaluatorScope.evaluate(entry.getKey(), entry.getValue());
				evaluatorAllNegators.evaluate(entry.getKey(), entry.getValue());
			}
			
			evaluatorScope.printAllResults();
			
			CorpusWriterXML writer = new CorpusWriterXML(new File(xmlPath + "//vlastne"));
			writer.saveCorpus(ownDocuments);
			
			corpus = new HashMap<String, List<Document>>();
 	
			reader = new CorpusReaderBioScope(new File("F://Java//Dataset//BioScope"));
			List<Document> bioDocuments = reader.createCorpus();
			corpus.put("BioScope", bioDocuments);
			
			EvaluatorBioScopeNegators evaluatorBioScopeNegators = new EvaluatorBioScopeNegators();
			EvaluatorBioScopeScope evaluatorBioScopeScope = new EvaluatorBioScopeScope();
			
			for(Map.Entry<String, List<Document>> entry : corpus.entrySet()) {
				evaluatorStatistics.evaluate(entry.getKey(), entry.getValue());;
				evaluatorBioScopeNegators.evaluate(entry.getKey(), entry.getValue());
				evaluatorBioScopeScope.evaluate(entry.getKey(), entry.getValue());
			}
		}

}
