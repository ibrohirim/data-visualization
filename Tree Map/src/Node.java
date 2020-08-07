import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Node {
	
	private File file;
	private List<Node> children;
	private long sum;
	private Color color;
	private String colorScheme;
	private Rectangle rect;
	
	public Node(File f) {
		children = new ArrayList<Node>();
		file = f; 
		sum = setSum(file);
	}
	
	public  List<Node> getChildren() {
		return children;
	}
	
	public void setColorScheme(String colorScheme) {
		if(this.getChildren().isEmpty()) {
			this.colorScheme = colorScheme;
		} else {
			for (Node n : this.getChildren()) {
				n.setColorScheme(colorScheme);
			}
		}
	}
	
	private long setSum(File f) {
		long s = 0;
		if(f.isFile()) {
			s += f.length();
		} else {
			for(File fil : f.listFiles()) {
				Node child = new Node(fil);
				children.add(child);
				s += child.getSum();
			}
		}
		return s;
	}
	
	public long getSum() {
		return sum;
	}
	
	private void setPaint() {
		if(colorScheme == "type") {
			String ext = getFileExtension(file);
			ext.toLowerCase();
			switch(ext) {
				case "txt": case "rtf":
					color = new Color(118, 121, 124);
					break;
				case "docx": case "doc":
					color = new Color(0, 26, 255);
					break;
				case "aif": case "aiff":
					color = new Color(196, 0, 255);
					break;
				case "au": case "ra": case "wav":
					color = new Color(255, 239, 0);
					break;
				case "avi":
					color = new Color(171, 9 , 255);
					break;
				case "bat":
					color = new Color(255, 85, 0);
					break;
				case "bmp":
					color = new Color(0, 255, 255);
					break;
				case "java":
					color = new Color(88, 50, 9);
					break;
				case "class":
					color = new Color(10, 220, 150);
					break;
				case "csv": case "xls": case "xlsx":
					color = new Color(5, 159, 9);
					break;
				case "cvs":
					color = new Color(186, 0, 43);
					break;
				case "dbf":
					color = new Color(0, 50, 186);
					break;
				case "dif":
					color = new Color(230, 255, 0);
					break;
				case "eps":
					color = new Color(236, 54, 139);
					break;
				case "exe":
					color = new Color(255, 0, 0);
					break;
				case "fm3": case "fm1": case "fm2":
					color = new Color(0, 111, 255);
					break;
				case "gif":
					color = new Color(0, 201, 201);
					break;
				case "hqx":
					color = new Color(157, 205, 94);
					break;
				case "htm": case "html":
					color = new Color(207, 70, 242);
					break;
				case "mac":
					color = new Color(207, 70, 242);
					break;
				case "map":
					color = new Color(70, 242, 167);
					break;
				case "mdb":
					color = new Color(6, 134, 78);
					break;
				case "mid": case "midi":
					color = new Color(187, 126, 202);
					break;
				case "mov": case "qt":
					color = new Color(102, 13, 255);
					break;
				case "mtb": case "mtw":
					color = new Color(153, 153, 51);
					break;
				case "pdf":
					color = new Color(205, 0, 0);
					break;
				case "p65": case "t65":
					color = new Color(0, 6, 91);
				case "png": case "jpg": case "jpeg":
					color = new Color(171, 255, 0);
					break;
				case "ppt": case "pptx":
					color = new Color(209, 9, 49);
					break;
				case "psd":
					color = new Color(183, 31, 0);
					break;
				case "psp":
					color = new Color(26, 128, 91);
					break;
				case "qxd":
					color = new Color(26, 70, 128);
					break;
				case "sit": case "tar": case "zip":
					color = new Color(213, 219, 51);
					break;
				case "tif":
					color = new Color(153, 153, 102);
					break;
				case "wks": case "wk3": case "wak2":
				case "wk1":
					color = new Color(11, 82, 0);
					break;
				case "wpd": case "wp5":
					color = new Color(0, 124, 103);
					break;
				default:
					color = new Color(255, 255, 255);
					break;
			}
		} else if(colorScheme == "age") {
			long fAge = file.lastModified();
			Calendar year = Calendar.getInstance();
			year.add(Calendar.YEAR, -1);
			Calendar sixMonths = Calendar.getInstance();
			sixMonths.add(Calendar.MONTH, -6);
			Calendar threeMonths = Calendar.getInstance();
			threeMonths.add(Calendar.MONTH, -3);
			Calendar month = Calendar.getInstance();
			month.add(Calendar.MONTH, -1);
			if(fAge < year.getTimeInMillis()) {
				color = new Color(0, 0, 0);
			} else if(fAge > year.getTimeInMillis() && fAge < sixMonths.getTimeInMillis()) {
				color = new Color(61, 59, 59);
			} else if(fAge > sixMonths.getTimeInMillis() && fAge < threeMonths.getTimeInMillis()) {
				color = new Color(102, 101, 101);
			} else if(fAge > threeMonths.getTimeInMillis() && fAge < month.getTimeInMillis()) {
				color = new Color(169, 169, 169);
			} else {
				color = new Color(255, 255, 255);
			}
		} else if(colorScheme == "none") {
			color = new Color(255, 255, 255);
		} else if(colorScheme == "permissions") {
			if(file.canExecute() && !file.canRead() && !file.canWrite()) {
				color = new Color(255, 0, 0);
			} else if(file.canWrite() && !file.canExecute() && !file.canRead()) {
				color = new Color(0, 0, 255);
			} else if(file.canWrite() && file.canExecute() && !file.canRead()) {
				color = new Color(200, 0, 200);
			} else if(file.canRead() && !file.canWrite() && !file.canExecute()) {
				color = new Color(0, 213, 255);
			} else if(file.canRead() && file.canExecute() && !file.canWrite()) {
				color = new Color(253, 138, 253);
			} else if(file.canRead() && file.canWrite() && !file.canExecute()) {
				color = new Color(0, 150, 255);
			} else if(file.canRead() && file.canWrite() && file.canExecute()) {
				color = new Color(0, 255, 0);
			} else {
				color = new Color(0, 0, 0);
			}
		} else {
			
		}
	}
	
    private String getFileExtension(File f) {
        String fileName = f.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
	
	public void draw(Graphics2D g, Rectangle r, String orientation, double x, double y) {
		this.rect = r;
		double w = r.getWidth();
		double h = r.getHeight();
		if(this.file.isFile()) {
			setPaint();
			g.setColor(color);
			g.fill(r);
			g.setColor(Color.BLACK);
			g.draw(r);
		} else {
			if(orientation == "ver") {
				double pixelsPerByte = w/sum;
				for(Node c : children) {
					double cWidth = c.getSum()*pixelsPerByte;
					Rectangle newR = new Rectangle((int)x, (int)y, (int)cWidth, (int)h);
					c.draw(g, newR, "hor", x, y);
					x+= cWidth;
				}
			} else {
				double pixelsPerByte = h/sum;
				for(Node c : children) {
					double cHeight = c.getSum()*pixelsPerByte;
					Rectangle newB = new Rectangle((int)x, (int)y, (int)w, (int)cHeight);
					c.draw(g, newB, "ver", x, y);
					y+= cHeight;
				}
			}
		}
	}
	
	public File getFile() {
		return this.file;
	}
	
	public String in(int x, int y) {
		if(this.file.isFile()) {
			return this.file.getPath() + " size: (" + humanReadableByteCount(this.sum, true) + ")";
		} else {
			for(Node n : this.getChildren()) {
				if(n.getRect().contains(x, y)) {
					return n.in(x, y);
				}
			}
			return "";
		}
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
	private String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
