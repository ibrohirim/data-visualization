import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;

public class Vis extends JPanel implements MouseListener, MouseMotionListener {

	private Boolean initial;
	private List<Float> x;
	private List<Float> y;
	private List<Float> xPoints;
	private List<Float> yPoints;
	private List<Ellipse2D> points;
	private HashMap<Ellipse2D, String> hash;
	private Rectangle box;
	private Point mouseDown;
	private float maxX;
	private float minX;
	private float maxY;
	private float minY;
	private float newMaxX;
	private float newMinX;
	private float newMaxY;
	private float newMinY;
	private int mWidth;
	private int mHeight;
	private Boolean newMax;
	private String xString;
	private String yString;
	
	//constructor
	public Vis() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		initial = true;
		y = new ArrayList<Float>();
		x = new ArrayList<Float>();
		xPoints = new ArrayList<Float>();
		yPoints = new ArrayList<Float>();
		points = new ArrayList<Ellipse2D>();
		box = null;
		mWidth = 0;
		mHeight = 0;
		newMax = false;
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		int width = getWidth();
		int height = getHeight();
		int wMar = 50;
		mWidth = width - (wMar + wMar);
		int hMar = 50;
		mHeight = height - (hMar + hMar);
		//initial screen
		if(initial == true) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.BLACK);
			g.drawString("Choose a query", width/2, height/2);
		} else {
			//queried screen
			Color b = new Color(33, 155, 209);
			g.setColor(b);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.BLACK);
			//set max Vals
			if(newMax == true) {
				maxX = newMaxX;
				minX = newMinX;
				maxY = newMaxY;
				minY = newMinY;
				yPoints.clear();
				xPoints.clear();
			} else {
				maxX = Collections.max(x);
				minX = Collections.min(x);
				if(minX > 0) {
					minX = 0;
				}
				maxY = Collections.max(y);
				minY = Collections.min(y);
				if(minY > 0) {
					minY = 0;
				}
			}
			//drawDots
			Color d = new Color(155, 209, 33);
			g.setColor(d);
			
			for(int i = 0; i < x.size(); i++) {
				float xP = xPos(mWidth, maxX, minX, x.get(i));
				xPoints.add(xP);
				float yP = yPos(mHeight, maxY, minY, y.get(i));
				yPoints.add(yP);
				Ellipse2D point = new Ellipse2D.Double(xP+wMar, yP+hMar, width*0.006, width*0.006);
				points.add(point);
				hash.put(point, x.get(i) + " " + y.get(i));
				g.fill(point);
			}
			
			Color l = new Color(209, 33, 74);
			g.setColor(l);
			
			BasicStroke s = new BasicStroke((float) (width*0.00008));
			g.setStroke(s);
			//draw Axis'
			int ovalDimension = (int) (width*0.006);
			float lineY = Collections.max(yPoints);
			lineY = (int) ((mHeight+hMar-ovalDimension) - lineY);
			g.drawLine(wMar, (int) lineY, wMar, mHeight+hMar+ovalDimension);
			g.drawLine(wMar, mHeight+hMar+ovalDimension, width-wMar+ovalDimension, mHeight+hMar+ovalDimension);
			for(int i = 0; i < 5; i++) {
				float aY = (float) (maxY*(i)/4.0);
				int y2;
				if(i == 4) {
					int numHeight = g.getFontMetrics().getHeight();
					y2 = (int) (((mHeight+hMar-ovalDimension) - (Collections.max(yPoints)*((i+0.0)/4.0))+numHeight/2));
				} else {
					y2 = (int) ((mHeight+hMar+ovalDimension) - (Collections.max(yPoints)*((i+0.0)/4.0)));
				}
				String num = String.format("%.2f", aY);
				int numMetrics = g.getFontMetrics().stringWidth(num);
				g.drawString(num, wMar-numMetrics, y2);
			}
			
			for(int i = 0; i < 5; i++) {
				float aX = (float) (maxX*(i)/4.0);
				System.out.println(i + " " + aX + " " + maxX);
				int y2 = mHeight+hMar+ovalDimension;
				String num = String.format("%.2f", aX);
				int numMetrics = g.getFontMetrics().getHeight();
				int xC = (int) (wMar + (Collections.max(xPoints)*((i+0.0)/4)));
				g.drawString(num, xC, y2+numMetrics);
			}
			//dragged box
			Color t = new Color(209, 33, 74, 157);
			g.setColor(t);
			
			if(box != null) {
				g.fill(box);
			}
			
			xPoints.clear();
			yPoints.clear();
		}
	}
	//is it initial screen
	public void setInitial(Boolean b) {
		initial = b;
	}
	//set y list
	public void setY(List<Float> param1) {
		y = param1;
		hash = new HashMap<Ellipse2D, String>(y.size());
	}
	// set x list
	public void setX(List<Float> param2) {
		x = param2;
	}
	//set xPos
	private float xPos(int w, float max, float min, float d) {
		float x = w * ((d - min)/(max -min));
		return x;
	}
	//set yPos
	private float yPos(int h, float max, float min, float d) {
		float y = h-(h*(d - min)/(max-min));
		return y;
	}
	//setX y strings
	public void setStrings(String y, String x) {
		xString = x;
		yString = y;
	}
	//clear all lists 	
	public void clearLists() {
		y.clear();
		x.clear();
		xPoints.clear();
		yPoints.clear();
		points.clear();
		if (hash != null) {
			hash.clear();
		}
	}
	

	//compute new axis max and min vals
	private void setNewMaxVals(int p1x, int p1y, int p2x, int p2y) {
		newMinX = ((float) p1x/mWidth) * (maxX - minX) + minX;
		newMinY = ((mHeight - (float) p2y)/mHeight) * (maxY - minY) + minY;
		newMaxX = (((float) p2x/mWidth) * (maxX - minX)) + minX;
		newMaxY = ((mHeight - (float) p1y)/mHeight) * (maxY - minY) + minY;
	}
	// set new Max variable to determine if a new maX is set
	public void setBoolMax(Boolean b) {
		newMax = b;
	}
	//mouse dragged event
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		box.setFrameFromDiagonal(mouseDown.x, mouseDown.y, x, y);
		repaint();
	}
	//mouse moved event
	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		for(int i = 0; i < points.size(); i++) {
			if(points.get(i).contains(x, y)) {
				String s = hash.get(points.get(i));
				String[] split = s.split(" ");
				String tip = xString + ": " + split[0] + ", " + yString + ": "+ split[1];
				setToolTipText(tip);
				break;
			} else {
				setToolTipText(null);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	//mouse pressed event
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouseDown = new Point(x,y);
		box = new Rectangle();
	}
	//mouse released event listener
	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Point mouseUp = new Point(x,y);
		box.setFrameFromDiagonal(mouseDown.x, mouseDown.y, mouseUp.x, mouseUp.y);
		//do matching here to change range of scatterplot
		xPoints.clear();
		yPoints.clear();
		points.clear();
		setNewMaxVals(mouseDown.x, mouseDown.y, mouseUp.x, mouseUp.y);
		setBoolMax(true);
		box = null;
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}