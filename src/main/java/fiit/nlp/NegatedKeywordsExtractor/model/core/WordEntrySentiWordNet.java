package fiit.nlp.NegatedKeywordsExtractor.model.core;

public class WordEntrySentiWordNet {
	public String pos;
	public String id;
	public double positiveScore;
	public double negativeScore;
	public String synset;
	
	public WordEntrySentiWordNet() {
	}
	
	public WordEntrySentiWordNet(String pos, String id, double positiveScore, double negativeScore, String synset) {
		this.pos = pos;
		this.id = id;
		this.positiveScore = positiveScore;
		this.negativeScore = negativeScore;
		this.synset = synset;
	}
	
	public boolean isNegative() {
		return negativeScore - positiveScore > 0.485;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + ((synset == null) ? 0 : synset.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordEntrySentiWordNet other = (WordEntrySentiWordNet) obj;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (synset == null) {
			if (other.synset != null)
				return false;
		} else if (!synset.equals(other.synset))
			return false;
		return true;
	}
}
