import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Vis extends JPanel {
	
	private boolean initial = true;
	private List<Float> counts = new ArrayList<Float>();
	private List<Integer> heights = new ArrayList<Integer>();
	private List<String> strings = new ArrayList<String>();
	private List<Float> fractions = new ArrayList<Float>();
	private List<Float> yAxis = new ArrayList<Float>();
	private Boolean bar = true;
	private Boolean line = false;
	
	public Vis() {
		super();
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		//screen dimensions
		int width = getWidth();
		int height = getHeight();
		int leftMar = width/12;
		int bottom = height-(height/14);
		int top = height/10;
		//initial screen
		if(initial == true) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.BLACK);
			g.drawString("Select a query", width/2, height/2);
		} else {
			//if bar chart
			if(bar == true && line == false) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, width, height);
				int barWidth = width/counts.size();
				//get the fractions and heights
				setFractions(counts, getMax(counts));
				setHeights(fractions, height);
				//draw bars
				g.setColor(Color.BLUE);
				int x = 0;
				for(int i = 0; i < counts.size(); i++) {
					int h = heights.get(i);
					x = leftMar+(barWidth*i);
					int y= bottom-heights.get(i);
					int w = barWidth-(width/8);
					g.fillRect(x, y, w, h);
				}
				//set xAxis
				setYAxis(getMax(counts));
				//draw x and y axis
				g.setColor(Color.BLACK);
				g.setFont(new Font("TimesNewRoman", Font.PLAIN, 12));
				g.drawLine(leftMar-5, bottom - getMaxInt(heights), leftMar-5, bottom+5);
				g.drawLine(leftMar-5, bottom+5, x+(barWidth-(width/8)), bottom+5);
				//draw text for x and y axis
				for(int i = 0; i < yAxis.size(); i++) {
					int y2 = (int) (bottom - (getMaxInt(heights)*((i+1.0)/4)));
					g.drawString(String.format("%.2f", yAxis.get(i)), leftMar-50, y2+5);
				}
				g.drawString("0.0", leftMar-50, bottom+10);
				for(int i = 0; i < strings.size(); i++) {
					g.drawString(strings.get(i), leftMar+(barWidth*i), bottom+25);
				}
				//clear lists so it can resize
				heights.clear();
				fractions.clear();
				
			} else {
				//line chart
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, width, height);
				int barWidth = width/counts.size();
				//positions of the dots
				setFractions(counts, getMax(counts));
				setHeights(fractions, height);
				//draw the dots
				g.setColor(Color.BLUE);
				int x = 0;
				for(int i = 0; i < counts.size(); i++) {
					x = (leftMar+(barWidth*i)) + (barWidth/6);
					int y = bottom-heights.get(i);
					g.fillOval(x, y, 25, 25);
				}
				//stroke of lines
				BasicStroke l = new BasicStroke(7);
				g.setStroke(l);
				//draw lines
				for(int i = 0; i < counts.size() - 1; i++) {
					int x1 = ((leftMar+(barWidth*i)) + (barWidth/6))+(25/2);
					int y1 = (bottom-heights.get(i))+(25/2);
					int x2 = ((leftMar+(barWidth*(i+1))) + (barWidth/6))+(25/2);
					int y2 = (bottom-heights.get(i + 1))+(25/2);
					g.drawLine(x1, y1, x2, y2);
				}
				//reset stroke for x and y axis
				BasicStroke s = new BasicStroke(1);
				g.setStroke(s);
				//set and draw x and y axis
				setYAxis(getMax(counts));
				g.setColor(Color.BLACK);
				g.setFont(new Font("TimesNewRoman", Font.PLAIN, 12));
				g.drawLine(leftMar-5, bottom - getMaxInt(heights), leftMar-5, bottom+5);
				g.drawLine(leftMar-5, bottom+5, (x+(barWidth-(width/8)))-(barWidth/6), bottom+5);
				
				for(int i = 0; i < yAxis.size(); i++) {
					int y2 = (int) (bottom - (getMaxInt(heights)*((i+1.0)/4)));
					g.drawString(String.format("%.2f", yAxis.get(i)), leftMar-50, y2+5);
				}
				g.drawString("0.0", leftMar-50, bottom+10);
				for(int i = 0; i < strings.size(); i++) {
					g.drawString(strings.get(i), leftMar+(barWidth*i), bottom+25);
				}
				heights.clear();
				fractions.clear();
			}
		}
	}
	
	private int getMinInt(List<Integer> f) {
		int min = f.get(0);
		for(Integer fl : f) {
			if(min > fl){
				min = fl;
			}
		}
		return min;
	}

	public void setCounts(List<Float> c) {
		counts = c;
	}
	
	public float getMax(List<Float> c) {
		float max = 0;
		for(Float i : c) {
			if(i > max) {
				max = i;
			}
		}
		return max;
	}
	
	public float getMin(List<Float> f) {
		float min = f.get(0);
		for(Float fl : f) {
			if(min > fl){
				min = fl;
			}
		}
		return min;
	}
	
	public int getMaxInt(List<Integer> c) {
		int max = 0;
		for(Integer i : c) {
			if(i > max) {
				max = i;
			}
		}
		return max;
	}
	
	public void setFractions(List<Float> c, float max) {
		
		for(Float f : c) {
			fractions.add(f/max);
		}
	}
	
	public void setHeights(List<Float> fr, int height) {
		for(Float f : fr) {
			heights.add((int) (f*(height-(height/10))));
		}
	}

	public void clearLists() {
		counts.clear();
		heights.clear();
		strings.clear();
		fractions.clear();
		yAxis.clear();
	}

	public void setStrings(List<String> majors) {
		
		strings = majors;
		
	}

	public void setInitial(boolean b) {
		initial = b;
	}
	
	public void setYAxis(float max) {
		for(float i = 0; i < 4; i++) {
			float y = (float) (max*((i+1.0)/4.0));
			yAxis.add(y);
		}
	}
	
	public void setBar(Boolean b) {
		bar = b;
	}
	
	public void setLine(Boolean b) {
		line = b;
	}
}
