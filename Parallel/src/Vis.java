import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Vis extends JPanel {

	private ArrayList<Axis> axes;
	private int mWidth;
	private int mHeight;
	
	public Vis() {
		super();
		axes = new ArrayList<Axis>();
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		int width = getWidth();
		int height = this.getHeight();
		int wMar = 25;
		mWidth = width - (wMar + wMar);
		int hMar = 25;
		mHeight = height - (hMar + hMar);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		Set<String> unique;
		if(!axes.isEmpty()) {
			//determine string fractions
			for(Axis a : axes) {
				if(a.getNumData() != null) {
					a.setFrations(a.getMax());
				} else {
					unique = new HashSet<>(a.getTextData());
					ArrayList<Double> dubs = new ArrayList<Double>();
					for(int i = 0; i < unique.size(); i++){
						dubs.add((i+0.0)/(unique.size()));
					}

					ArrayList<Double> frf = new ArrayList<Double>();
					for(int z = 0; z < a.getTextData().size(); z++) {
						int f = 0;
						for(String u : unique) {
							if(a.getTextData().get(z).equals(u)) {
								frf.add(dubs.get(f));
							}
							f++;
						}
					}
					a.setOutFractions(frf);
				}
			}
			//draw lines and axes
			g.setColor(Color.BLACK);
			int barWidth = mWidth/axes.size();
			for(int i = 0; i < axes.get(2).getNumData().size(); i++){
				int[] x = new int[axes.size()];
				int[] y = new int[axes.size()];
				for(int j = 0; j < axes.size(); j++) {
					Axis a = axes.get(j);
					if(a.getNumData() != null && i < a.getFractions().size()) {
						ArrayList<Double> da = a.getFractions();
						x[j] = wMar+(barWidth*j);
						y[j] = (int) (mHeight - (da.get(i)*mHeight));
						g.setColor(Color.RED);
						double pr = a.getMax()/j;
						g.drawString(Double.toString(pr), x[j], (int)pr);
						g.drawString(a.getColumnName(), x[j]-(g.getFontMetrics().stringWidth(a.getColumnName())/2), mHeight+25);
						g.drawLine(x[j], 0, x[j], mHeight);
					} else {
						ArrayList<String> ne = a.getTextData();
						if(ne != null && i < ne.size()) {
							ArrayList<Double> nefr = a.getFractions();
							x[j] = wMar+(barWidth*j);
							y[j] = (int) (mHeight - (nefr.get(i)*mHeight));
							g.setColor(Color.RED);
							g.drawString(a.getColumnName(), x[j]-(g.getFontMetrics().stringWidth(a.getColumnName())/2), mHeight+25);
							g.drawLine(x[j], 0, x[j], mHeight);
							g.drawString(a.getTextData().get(i), x[j]+10, y[j]);
						}
					}
				}
				g.setColor(Color.BLACK);
				g.drawPolyline(x, y, axes.size());
			}			
		}
	}

	public ArrayList<Axis> getAxes() {
		return axes;
	}

	public void setAxes(ArrayList<Axis> axes) {
		this.axes = axes;
	}
	
	public void clearAxes() {
		if(this.axes!= null) {
			this.axes.clear();
		}
	}
	
}
