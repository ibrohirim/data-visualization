import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	private MainPanel panel;
	private ArrayList<String> algorithmKeys;
	
	public Main() {
		
		JMenuBar mb = setMenuBar();
		setJMenuBar(mb);
		panel = new MainPanel();
		setContentPane(panel);
		//keys for stopping threads
		algorithmKeys = new ArrayList<String>();
		algorithmKeys.add("bubble");
		algorithmKeys.add("selection");
		algorithmKeys.add("heapSort");
		
		setPreferredSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Sorting Visualization");
		pack();
		setVisible(true);
	}
	
	//menubar creation
	private JMenuBar setMenuBar() {
		
		JMenuBar bar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu algorithms = new JMenu("Algorithms");
		JMenuItem chooseFile = new JMenuItem("Choose a file");
		JMenuItem bubbleSort = new JMenuItem("Bubble Sort");
		JMenuItem selectionSort = new JMenuItem("Selection Sort");
		JMenuItem heapSort = new JMenuItem("HeapSort");
		
		chooseFile.addActionListener(e-> {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
			fc.setFileFilter(filter);
			int returnedVal = fc.showOpenDialog(null);
			if(returnedVal == JFileChooser.APPROVE_OPTION) {
				ArrayList<Integer> list = new ArrayList<Integer>();
				try {
					Scanner s = new Scanner(fc.getSelectedFile());
					while(s.hasNext()) {
						list.add(s.nextInt());
					}
					s.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				panel.seList(list);
				panel.resetNums();
				panel.interrputThreads(algorithmKeys);
				repaint();
			}
		});
		
		bubbleSort.addActionListener( e-> {
			panel.resetNums();
			panel.interrputThreads(algorithmKeys);
			try {
				panel.bubbleSort();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			repaint();
		});
		
		selectionSort.addActionListener( e-> {
			panel.interrputThreads(algorithmKeys);
			panel.resetNums();
			try {
				panel.selectionSort();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			repaint();
		});
		
		heapSort.addActionListener(e-> {
			panel.interrputThreads(algorithmKeys);
			panel.resetNums();
			try {
				panel.heapSort();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			repaint();
		});
		
		
		fileMenu.add(chooseFile);
		algorithms.add(bubbleSort);
		algorithms.add(selectionSort);
		algorithms.add(heapSort);
		bar.add(fileMenu);
		bar.add(algorithms);
		
		return bar;
	}

	//run the project
	public static void main(String[] args) {
	
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
		
	}

}
