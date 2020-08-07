import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	
	private String algorithm;
	private int count;
	private int exchange;
	private Map<String, Thread> threads;
	private List<Integer> numbers;
	private List<Bar> bars;
	private List<Integer> list;
	private int leftMargin;
	private int yMargin;
	private Line2D yAxis ;
	private Line2D xAxis;
	private int one;
	private int two;
	private int speed;
	private Boolean flag;
	private Boolean compar;

	public MainPanel() {
		super();
		algorithm = "";
		count = 0;
		exchange = 0;
		speed = 250;
		numbers = null;
		threads = new HashMap<String, Thread>();
		bars = new ArrayList<Bar>();
		list = new ArrayList<Integer>();
		leftMargin = 40;
		flag = false;
		compar = false;
		yMargin = 40;
		one = 0;
		two = 0;
	}
	
	//logic  to compute the bar sizes and colors
	public void prerender() {
		double max = 0; 
		for(Integer n : numbers) {
			if(n > max) {
				max = n;
			}
		}
		
		int barWidth = (int)((xAxis.getX2()-xAxis.getX1())/numbers.size());
		int xPos = 0;
		for(int i = 0; i < numbers.size(); i++) {
			double bH = (numbers.get(i)/max)*(yAxis.getY2() - yAxis.getY1());
			xPos = leftMargin+(barWidth*i)+(barWidth/5);
			if(flag) {
				if(one == numbers.get(i) || two == numbers.get(i)) {
					Color c;
					if(compar == true) {
						c = new Color(255, 255, 0);
					} else {
						c = new Color(255, 0, 0);
					}
					bars.add(new Bar(xPos, (int)((getHeight() - yMargin)-bH), barWidth-(barWidth/5), (int)bH, numbers.get(i), c));
				} else {
					bars.add(new Bar(xPos, (int)((getHeight() - yMargin)-bH), barWidth-(barWidth/5), (int)bH, numbers.get(i), Color.GREEN));
				}
			} else {
				bars.add(new Bar(xPos, (int)((getHeight() - yMargin)-bH), barWidth-(barWidth/5), (int)bH, numbers.get(i), Color.GREEN));
			}
		}
	}
	
	//paint Component to draw all
	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D)g1;
		int width = getWidth();
		int height = getHeight();
		
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, width, height);
		if(!list.isEmpty()) {
			g.setColor(Color.BLACK);
			g.drawString(algorithm + " comparisons: " + Integer.toString(count) + " exchanges: " + Integer.toString(exchange), 40, 20);
			//axes
			yAxis = new Line2D.Float(leftMargin, yMargin, leftMargin, height-yMargin);
			g.draw(yAxis);
			xAxis = new Line2D.Float(leftMargin, height-yMargin, width-leftMargin, height-yMargin);
			g.draw(xAxis);
			//prerendered drawing
			prerender();
			for(Bar b : bars) {
				b.draw(g);
			}
			bars.clear();
		}
	}
	
	//bubblesort thread
	public void bubbleSort() throws InterruptedException {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				count = 0;
				exchange = 0;
				algorithm = "Bubble Sort";
				for(int i = 0; i < numbers.size() - 1; i++) {
					for(int j = numbers.size() - 1; j >= i + 1; j--) {
						one = numbers.get(j);
						two = numbers.get(j-1);
						flag = true;
						compar = true;
						count++;
						repaint();
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e1) {
							return;
						}
						if(numbers.get(j) < numbers.get(j - 1)) {
							compar = false;
							repaint();
							try {
								Thread.sleep(speed);
							} catch (InterruptedException e) {
								return;
							}
							int temp = numbers.get(j);
							numbers.set(j, numbers.get(j-1));
							numbers.set(j-1, temp);
							exchange++;
							repaint();
							try {
								Thread.sleep(speed);
							} catch (InterruptedException e) {
								return;
							}
						}
						one = 0;
						two = 0;
						flag = false;
					}
				}
				repaint();
			}
		});
		t.join();
		t.start();
		threads.put("bubble", t);
	}
	//selection sort thread
	public void selectionSort() throws InterruptedException {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				count = 0;
				exchange = 0;
				algorithm = "Selection Sort";
				for(int i = 0; i < numbers.size() - 1; i++) {
					int temp = numbers.get(i);
					one = temp;
					int min = temp;
					int minIndex = i;
					for(int j = i; j < numbers.size(); j++) {
						two = numbers.get(j);
						if(numbers.get(j) < min) {
							min = numbers.get(j);
							minIndex = j;
						}
						count++;
						//System.out.println("Compared " + numbers.get(j) + " " + min);
						flag = true;
						compar = true;
						repaint();
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							return;
						}
						compar = false;
					}
					flag = true;
					int exch = numbers.get(minIndex);
					two = exch;
					repaint();
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						return;
					}
					numbers.set(i, exch);
					numbers.set(minIndex, temp);
					exchange++;
					//System.out.println("Exchanged " + minIndex + " " + temp);
					repaint();
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						return;
					}
					flag = false;
				}
				repaint();
			}
			
		});
		t.start();
		threads.put("selection", t);
	}
	//heap sort thread needs more work
	public void heapSort() throws InterruptedException {
		Thread f = new Thread(new Runnable() {
			@Override
			public void run() {
				count = 0;
				exchange = 0;
				one=-1;
				two = -1;
				algorithm = "Heap Sort";
				int n = numbers.size();
				
				for(int i = n/2 -1; i >=0; i--) {
					heapify(numbers, n, i, Thread.interrupted());
				}
				
				for(int i = n-1; i >=0; i--) {
					exchange++;
					flag = true;
					one = numbers.get(0);
					two = numbers.get(i);
					repaint();
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						return;
					}
					int temp = numbers.get(0);
					numbers.set(0, numbers.get(i));
					numbers.set(i, temp);
					repaint();
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						return;
					}
					
					heapify(numbers, i, 0, Thread.interrupted());
				}
				flag = false;
				one = -1;
				two = -1;
				repaint();
			}
			
			private void heapify(List<Integer> arr, int n, int i, boolean b) {
				if(!b) {
					int largest = i;
					int l = 2*i +1;
					int r = 2*i +2;
					
					if(l < n && arr.get(l) > arr.get(largest)) {
						one = arr.get(l);
						two = arr.get(largest);
						largest = l;
						flag = true;
						compar = true;
						count++;
						repaint();
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							return;
						}
						compar = false;
					}
					
					if(r < n && arr.get(r) > arr.get(largest)) {
						largest = r;
						count++;
						flag = true;
						compar = true;
						one = arr.get(r);
						two = arr.get(largest);
						repaint();
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							return;
						}
						compar = false;
					}
					
					if(largest != i) {
						one = arr.get(i);
						two = arr.get(largest);
						flag = true;
						compar = false;
						repaint();
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							return;
						}
						int swap = arr.get(i);
						arr.set(i, arr.get(largest));
						arr.set(largest, swap);
						exchange++;
						repaint();
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							return;
						}			
						heapify(arr, n, largest, b);
					} else {
						return;
					}
				}
			}
			
		});
		f.start();
		threads.put("heapSort", f);
	}
	//set the list of nums
	public void seList(ArrayList<Integer> list) {
		this.list = list;
	}
	//reset the list to be sorted
	public void resetNums() {
		this.numbers = new ArrayList<Integer>(list);
	}
	// interrup all threads that a running in the bakcground
	public void interrputThreads(ArrayList<String> keys) {
		for(String key : keys) {
			if(threads.containsKey(key) && !threads.get(key).isInterrupted()) {
				System.out.println(threads.get(key) + " " + key);
				threads.get(key).interrupt();
				System.out.println(threads.get(key).isInterrupted());
				threads.remove(key);
			}
		}
		count = 0;
		exchange = 0;
		algorithm = "";
		one=-1;
		two = -1;
	}
	
}
