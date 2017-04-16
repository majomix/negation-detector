package fiit.nlp.NegatedKeywordsExtractor.model;

public class ParadigmEntry {

	public String lemma;
	public String pos;
	
	public ParadigmEntry(String lemma, String pos) {
		this.lemma = lemma;
		this.pos = pos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lemma == null) ? 0 : lemma.hashCode());
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
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
		ParadigmEntry other = (ParadigmEntry) obj;
		if (lemma == null) {
			if (other.lemma != null)
				return false;
		} else if (!lemma.equals(other.lemma))
			return false;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		return true;
	}
}
