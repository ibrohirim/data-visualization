import java.util.ArrayList;

public class Axis {
	private String columnName;
	private ArrayList<String> textData;
	private ArrayList<Double> numData;
	private ArrayList<Double> fractions;
 	
	public Axis(String columnName, ArrayList<String> textData, ArrayList<Double> numData) {
		this.setColumnName(columnName);
		fractions = new ArrayList<Double>();
		if(textData != null) {
			this.setTextData(textData);
			this.setNumData(null);
		} else {
			this.setNumData(numData);
			this.setTextData(null);
		}
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public ArrayList<String> getTextData() {
		return textData;
	}

	public void setTextData(ArrayList<String> textData) {
		this.textData = textData;
	}

	public ArrayList<Double> getNumData() {
		return numData;
	}

	public void setNumData(ArrayList<Double> numData) {
		this.numData = numData;
	}
	
	public void setFrations(Double max) {
		for(Double d : numData) {
			fractions.add(d/max);
		}
	}
	
	public void setOutFractions(ArrayList<Double> fractions) {
		this.fractions = fractions;
	}
	
	public ArrayList<Double> getFractions() {
		return this.fractions;
	}
	
	public double getMin() {
		double min = numData.get(0);
		for(Double d : numData) {
			if(min > d){
				min = d;
			}
		}
		return min;
	}
	
	public double getMax() {
		double max = 0;
		for(Double d : numData) {
			if(d > max) {
				max = d;
			}
		}
		return max;
	}
}
