package fiit.nlp.NegatedKeywordsExtractor.model;

public class ScoreCalculator {
	private int truePositive;
	private int trueNegative;
	private int falsePositive;
	private int falseNegative;
	
	public ScoreCalculator() {
		
	}
	
	public ScoreCalculator(int truePositive, int trueNegative, int falsePositive, int falseNegative) {
		this.truePositive = truePositive;
		this.trueNegative = trueNegative;
		this.falsePositive = falsePositive;
		this.falseNegative = falseNegative;
	}
	
	public void incrementTruePositive() {
		truePositive++;
	}
	
	public void incrementTrueNegative() {
		trueNegative++;
	}
	
	public void incrementFalsePositive() {
		falsePositive++;
	}
	
	public void incrementFalseNegative() {
		falseNegative++;
	}
	
	public int getTruePositive() {
		return truePositive;
	}
	
	public int getTrueNegative() {
		return trueNegative;
	}
	
	public int getFalsePositive() {
		return falsePositive;
	}

	public int getFalseNegative() {
		return falseNegative;
	}
	
	public int getTotal() {
		return truePositive + trueNegative + falsePositive + falseNegative;
	}
	
	public double getPrecision() {
		return ((double) truePositive) / (truePositive + falsePositive);
	}
	
	public double getRecall() {
		return ((double) truePositive) / (truePositive + falseNegative);
	}
	
	public double getSpecificity() {
		return ((double) trueNegative) / (trueNegative + falsePositive);
	}
	
	public double getAccuracy() {
		return ((double) (truePositive + trueNegative)) / getTotal();
	}
	
	public double getFMeasure() {
		return 2 * getPrecision() * getRecall() / (getPrecision() + getRecall());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Total: " + getTotal() + "\n");
		sb.append("True positive: " + getTruePositive() + "\n");
		sb.append("True negative: " + getTrueNegative() + "\n");
		sb.append("False positive: " + getFalsePositive() + "\n");
		sb.append("False negative: " + getFalseNegative() + "\n");
		sb.append("Precision: " + getPrecision() + "\n");
		sb.append("Recall: " + getRecall() + "\n");
		sb.append("F1: " + getFMeasure() + "\n");
		sb.append("Specificity: " + getSpecificity() + "\n");
		sb.append("Accuracy: " + getAccuracy() + "\n");
		
		return sb.toString();
	}
}
