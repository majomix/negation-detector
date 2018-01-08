package fiit.nlp.NegatedKeywordsExtractor;

import java.util.ArrayList;
import java.util.List;

import fiit.nlp.NegatedKeywordsExtractor.model.core.AbstractAnnotatedWord;

public class TfIdfCalculator {
    public double tf(List<AbstractAnnotatedWord> doc, String term, boolean withNegation) {
        double result = 0;
        double negated = 0;
        for (AbstractAnnotatedWord word : doc) {
            if (term.equalsIgnoreCase(word.lemma))
            {
            	result++;
            	
            	if(word.negationTargetOfNode.size() > 0)
            	{
            		negated++;
            	}
            }
        }
        
        if(withNegation)
        {
        	result /= (1 + negated);
        }
        
        return Math.log(result);
    }

    public double idf(List<ArrayList<AbstractAnnotatedWord>> docs, String term) {
        double n = 0;
        for (List<AbstractAnnotatedWord> doc : docs) {
            for (AbstractAnnotatedWord word : doc) {
                if (term.equalsIgnoreCase(word.lemma)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log((1 + docs.size()) / n);
    }

    public double tfIdf(List<AbstractAnnotatedWord> doc, List<ArrayList<AbstractAnnotatedWord>> docs, String term, boolean withNegation) {
        return tf(doc, term, withNegation) * idf(docs, term);
    }
}
