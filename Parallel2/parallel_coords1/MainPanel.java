package parallel_coords1;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class MainPanel extends JPanel implements MouseListener, MouseMotionListener {

	boolean dirty;
	int w,h;
	List<Axis> axes;
	List<HyrumPolyline> lines;
	List<HyrumPolyline> deselectedLines;
	int numEntities;
	private int leftMargin = 40;
	private Point mouseDown;
	private Rectangle box;
	private HyrumPolyline highlighted;
	private boolean dragged;

	public MainPanel() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		dirty = false;
		axes = null;
		lines = new ArrayList<HyrumPolyline>();
		deselectedLines = new ArrayList<HyrumPolyline>();
		highlighted = null;
		dragged = false;
	}
	
	public void clearData() {
		for(HyrumPolyline h : deselectedLines) {
			h.setSelectable(true);
			h.setSelected(true);
		}
		deselectedLines.clear();
		if (highlighted != null) {
			highlighted = null;
		}
	}

	public void setAxes(List<Axis> ax, int count) {
		axes = ax;
		dirty = true;
		numEntities = count;
		repaint();
	}


	private void prerender(Graphics g) {
		w = getWidth();
		h = getHeight();
		
		if (axes != null) {
			int xbuffer = (w-leftMargin*2) / (axes.size()-1);
			int ybuffer = 20;
			int x = leftMargin;
			lines.clear();
			for (Axis ax : axes) {
				Line2D dim = new Line2D.Float(x,ybuffer,x,h-ybuffer);
				ax.setDimensions(dim);
				x += xbuffer;
			}
			for (int i=0; i<numEntities; ++i) {
				HyrumPolyline line = new HyrumPolyline();
				String data = "";
				for (int j=0; j<axes.size(); ++j) {
					line.addPoint(axes.get(j).getPixelPosition(i));
					data += axes.get(j).getPrintedValue(i);
				}
				line.setData(data);
				line.container();
				lines.add(line);
			}

		}
	}

	@Override
	public void paintComponent(Graphics g1) {
		if (w != getWidth() || h != getHeight() || dirty) {
			prerender(g1);
			dirty = false;
		}

		Graphics2D g = (Graphics2D)g1;

		//	draw blank background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		//draw all lines
		for (HyrumPolyline p : lines) {
			if(p.getSelected() && p.getSelectable()) {
				if(dragged) {
					p.setLineColor(Color.RED);
					p.setLineStroke(new BasicStroke(2));
				} else {
					p.setLineColor(Color.MAGENTA);
					p.setLineStroke(new BasicStroke(1));
				}
				p.draw(g);
			} else {
				Color c = new Color(105, 105, 105, 89);
				p.setLineColor(c);
				p.setLineStroke(new BasicStroke(1));
				p.draw(g);
			}
		}
		//draw selected lines over deselected ones
		for (HyrumPolyline p : lines) {
			if(p.getSelected() && p.getSelectable()) {
				if(dragged) {
					p.setLineColor(Color.RED);
					p.setLineStroke(new BasicStroke(2));
				} else {
					p.setLineColor(Color.MAGENTA);
					p.setLineStroke(new BasicStroke(1));
				}
				p.draw(g);
			}
		}
		//draw highlighted line
		if(highlighted != null) {
			highlighted.setLineColor(Color.GREEN);
			highlighted.setLineStroke(new BasicStroke(6));
			highlighted.draw(g);
			highlighted = null;
		}
		
		if (axes != null) {
			for (Axis x : axes) {
				x.draw(g);
			}
		}
		
		//dragged box
		Color t = new Color(209, 33, 74, 157);
		g.setColor(t);
		
		if(box != null) {
			g.fill(box);
		}
	}
	
	public void paint(Graphics g, HyrumPolyline hp) {
		((Graphics2D) g).draw((Shape) hp);
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		int x = m.getX();
		int y = m.getY();
		box.setFrameFromDiagonal(mouseDown.x, mouseDown.y, x, y);
		for(HyrumPolyline hp : lines) {
			Shape sh = hp.getShape();
			if(sh.intersects(box) && hp.getSelectable() == true) {
				hp.setSelected(true);
				if(hp.getSelectable()) {
					deselectedLines.remove(hp);
				}
			} else {
				hp.setSelected(false);
				if(!deselectedLines.contains(hp)) {
					deselectedLines.add(hp);
				}
			}
		}
		dragged = true;
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Point mouseP = new Point(x, y);
		for(HyrumPolyline hp : lines) {
			if(hp.getShape().contains(mouseP) && hp.getSelectable()) {
				highlighted = hp;
				break;
			}
		}
		if(highlighted != null && highlighted.getSelected()) {
			setToolTipText(highlighted.getData());
		} else {
			setToolTipText(null);
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouseDown = new Point(x,y);
		box = new Rectangle();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Point mouseUp = new Point(x,y);
		box.setFrameFromDiagonal(mouseDown.x, mouseDown.y, mouseUp.x, mouseUp.y);
		box = null;
		for(HyrumPolyline hp : deselectedLines) {
			hp.setSelectable(false);
			hp.setSelected(false);
		}
		dragged = false;
		repaint();
	}

}
