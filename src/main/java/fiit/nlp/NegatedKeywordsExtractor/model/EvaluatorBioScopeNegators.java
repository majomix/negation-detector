package fiit.nlp.NegatedKeywordsExtractor.model;

import java.util.Arrays;
import java.util.List;

import fiit.nlp.NegatedKeywordsExtractor.model.core.AbstractAnnotatedWord;

public class EvaluatorBioScopeNegators extends EvaluatorCorpusAllNegators {

	@Override
	protected boolean checkCurrentWord(AbstractAnnotatedWord word) {
		List<String> negationTypes = Arrays.asList("sbs", "adv", "att");
		return !negationTypes.contains(word.negator);
	}
}
