package fiit.nlp.Synpar;
import java.util.ArrayList;
import java.util.LinkedList;

import fiit.nlp.Synpar.SentenceToken;
import is2.data.SentenceData09;
import is2.io.IOGenerals;

public class Sentence {
	public String text;
	public String diacritics;
	public String spellchecking;
	public String parserType;
	public String outputType;
	public LinkedList<SentenceToken> tokens;
	
	public SentenceData09 toConll(Boolean predCandidate) {
		Integer index = 0;
		String[] ids = new String[this.tokens.size() + 1];
		String[] forms =  new String[this.tokens.size() + 1];
		String[] lemmas = new String[this.tokens.size() + 1];
		String[] pos    = new String[this.tokens.size() + 1];
		String[] feats  = new String[this.tokens.size() + 1];
		String[] dummy  = new String[this.tokens.size() + 1];
		int[] initHeads = new int[this.tokens.size() + 1];
		ArrayList<ArrayList<String>> splittedFeats = new ArrayList<ArrayList<String>>();
		
		ids[index] = index.toString();
		forms[index] = IOGenerals.ROOT;
		lemmas[index] = IOGenerals.ROOT_LEMMA;
		pos[index] = IOGenerals.ROOT_POS;
		feats[index] = IOGenerals.NO_TYPE;
		splittedFeats.add(new ArrayList<String>());
		
		for (SentenceToken token : this.tokens) {
			index++;
			
			ids[index] = index.toString();
			forms[index] = token.form;
			lemmas[index] = token.lemma;
			pos[index] = Character.toString(token.pos.charAt(0));
			
			dummy[index] = "_";
			initHeads[index] = -1;
			
			String features = Features.Create(token.pos, predCandidate);
			
			feats[index] = features;
			
			ArrayList<String> splitted_features = new ArrayList<String>();
			
			for (String feat : features.split("\\|")) {
				splitted_features.add(feat);
			}
			
			splittedFeats.add(splitted_features);
		}
		
		String[][] splitted_feats_array = featsListToArray(splittedFeats);
		
		SentenceData09 it = new SentenceData09();
		
		it.id = ids;
		it.forms = forms;
		it.lemmas = lemmas;
		it.plemmas = lemmas;
		it.gpos = pos;
		it.ppos = pos;
		it.ofeats = feats;
		it.pfeats = feats;
		it.feats = splitted_feats_array;
		it.heads = initHeads;
		it.pheads = initHeads;
		it.labels = dummy;
		it.plabels = dummy;
		it.fillp = dummy;
		return it;
	}
	
	private String[][] featsListToArray(ArrayList<ArrayList<String>> nested) {
		String[][] array = new String[nested.size()][];
		for (int i = 0; i < nested.size(); i++) {
			ArrayList<String> row = nested.get(i);
			array[i] = row.toArray(new String[row.size()]);
		}
		return array;
	}
}
