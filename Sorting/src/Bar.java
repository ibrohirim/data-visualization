import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bar {
	
	private int height, width, x, y, value;
	private Color barColor;
	private Rectangle b;
	
	public Bar(int x, int y, int width, int height, int value, Color c) {
		this.setHeight(height);
		this.setWidth(width);
		this.setX(x);
		this.setY(y);
		this.setValue(value);
		this.setBarColor(c);
		setB(new Rectangle(x, y, width, height));
	}
	
	public void draw(Graphics2D g) {
		g.setColor(barColor);
		g.fill(this.b);
		String val = Integer.toString(value);
		int valWidth = g.getFontMetrics().stringWidth(val);
		g.setColor(Color.BLACK);
		g.drawString(val,(x+width/2)-(valWidth/2), y+height+20);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Color getBarColor() {
		return barColor;
	}

	public void setBarColor(Color barColor) {
		this.barColor = barColor;
	}

	public Rectangle getB() {
		return b;
	}

	public void setB(Rectangle b) {
		this.b = b;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
