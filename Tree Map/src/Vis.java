import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Vis extends JPanel implements MouseListener, MouseMotionListener {
	
	private int topOfPanel;
	private Node root;

	public Vis() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		int width = getWidth();
		int height = getHeight();
		g.setColor(Color.WHITE);
		g.fillRect(0,topOfPanel, width, height - topOfPanel);
		Rectangle r = new Rectangle(0, topOfPanel, width, height - topOfPanel);
		root.draw(g, r, "ver", 0, topOfPanel);
	}
	
	public void setTopOfPanel(int t) {
		topOfPanel = t;
	}

	public void setRoot(Node r) {
		root = r;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		String tip = root.in(x, y);
		if(!tip.isEmpty()) {
			setToolTipText(tip);
		} else {
			setToolTipText(null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//TODO
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
