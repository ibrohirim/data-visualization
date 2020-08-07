/*********************************
 * This file is part of the "Interactive Fan Chart Demo"
 * by Geoffrey M. Draper.
 * 
 * Geoffrey M. Draper, author of this work, hereby grants
 * everyone permission to use and distribute this code,
 * with or without modifications for any purpose,
 * commercial or otherwise. 
 * 
 * It is requested, although not required, that derivative
 * works include an acknowledgment somewhere in the
 * derivative work's credits.
 *********************************/

package parallel_coords1;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
//This composite class gives me the precision of the new Path2D
//class, while still providing the low-level access to individual
//vertices I got accustomed to when using the legacy Polygon class.

//NOTE: I backported from Path2D.Double to GeneralPath to maintain
//compatibility with Java 1.5.

public class HyrumPolyline {
	private GeneralPath polygon;
	private Shape shape;
	private List<Point2D> points;
	private Color lineColor;
	private BasicStroke lineStroke;
	private Boolean selected;
	private Boolean selectable;
	private String data;

	public HyrumPolyline() {
		polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		points = new ArrayList<Point2D>();
		setData("");
		lineColor = new Color(0, 0, 0);
		setLineStroke(new BasicStroke(1));
		selected = true;
		selectable = true;
	}

	public HyrumPolyline(HyrumPolyline hp) {
		polygon = new GeneralPath(hp.polygon);
		points = new ArrayList<Point2D>(hp.points);
		setData("");
		lineColor = new Color(0, 0, 0);
		setLineStroke(new BasicStroke(1));
		selected = true;
		selectable = true;
	}
	
	public void container() {
		BasicStroke bs = new BasicStroke(1);
		shape = bs.createStrokedShape(polygon);
	}

	public void addPoint(final Point2D p) {
		addPoint(p.getX(), p.getY());
	}

	public void addPoint(final Double x, final Double y) {
		if (points.isEmpty()) {
			polygon.moveTo(x, y);
		} else {
			polygon.lineTo(x, y);
		}
		points.add(new Point2D.Double(x,y));
	}

	public Point2D getPointAt(final int index) {
		return points.get(index);
	}

	public int getNumPoints() {
		return points.size();
	}

	//trivial delegate methods
	public void closePath() {
		if (points.size() > 2) {
			polygon.closePath();
		}
	}

	public void reset() {
		polygon.reset();
		points.clear();
	}

	public void draw(final Graphics2D g) {
		g.setColor(lineColor);
		g.setStroke(lineStroke);
		g.draw(polygon);
	}

	public void draw(Graphics2D g, Color c) {
		g.setColor(c);
		g.draw(polygon);
	}
	
	public GeneralPath getGeneralPath() {
		return polygon;
	}
	
	public int pointsGetSize() {
		return points.size();
	}

	public Shape getShape() {
		return shape;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public BasicStroke getLineStroke() {
		return lineStroke;
	}

	public void setLineStroke(BasicStroke lineStroke) {
		this.lineStroke = lineStroke;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Boolean getSelectable() {
		return selectable;
	}

	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
