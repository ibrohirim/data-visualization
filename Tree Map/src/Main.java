import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Main extends JFrame{
	
	private Vis mainPanel;
	private Node root; 
	private String colorScheme;
	private String currentName;
	private JFileChooser c;
	
	public Main() {
		
		mainPanel = new Vis();
		setContentPane(mainPanel);
		
		c = new JFileChooser();
		c.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		JMenuBar mb = createMenu();
		setJMenuBar(mb);
		int size = mb.getHeight();
		mainPanel.setTopOfPanel(size);
		File current = new File(System.getProperty("user.dir"));
		c.setCurrentDirectory(current);
		currentName = current.getName();
		root = new Node(current);
		colorScheme = "type";
		root.setColorScheme(colorScheme);
		mainPanel.setRoot(root);
		
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tree Map for " + currentName);
		setVisible(true);
	}
	
	private JMenuBar createMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem select = new JMenuItem("Select Folder");
		JMenu subMenu = new JMenu("Select Color");
		JCheckBoxMenuItem byType = new JCheckBoxMenuItem("Color by file type", true);
		JCheckBoxMenuItem byAge = new JCheckBoxMenuItem("Color by age");
		JCheckBoxMenuItem none = new JCheckBoxMenuItem("No color");
		JCheckBoxMenuItem permissions = new JCheckBoxMenuItem("Color by permissions");
		
		select.addActionListener(e -> {
			
			int retrunedVal = c.showOpenDialog(null);
			if(retrunedVal == JFileChooser.APPROVE_OPTION) {
				root = new Node(c.getSelectedFile());
				root.setColorScheme(colorScheme);
				currentName = root.getFile().getPath();
				setTitle(currentName);
				c.setCurrentDirectory(root.getFile());
				mainPanel.setRoot(root);
				repaint();
			}
		});
		
		byType.addActionListener(e -> {
			colorScheme = "type";
			root.setColorScheme(colorScheme);
			byType.setSelected(true);
			byAge.setSelected(false);
			none.setSelected(false);
			permissions.setSelected(false);
			repaint();
		});
		
		byAge.addActionListener(e -> {
			colorScheme = "age";
			root.setColorScheme(colorScheme);
			byType.setSelected(false);
			byAge.setSelected(true);
			none.setSelected(false);
			permissions.setSelected(false);
			repaint();
		});
		
		none.addActionListener(e -> {
			colorScheme = "none";
			root.setColorScheme(colorScheme);
			byType.setSelected(false);
			byAge.setSelected(false);
			none.setSelected(true);
			permissions.setSelected(false);
			repaint();
		});
		
		permissions.addActionListener(e -> {
			colorScheme = "permissions";
			byType.setSelected(false);
			byAge.setSelected(false);
			none.setSelected(false);
			permissions.setSelected(true);
			root.setColorScheme(colorScheme);
			repaint();
		});
		
		subMenu.add(byType);
		subMenu.add(byAge);
		subMenu.add(permissions);
		subMenu.add(none);
		file.add(select);
		file.add(subMenu);
		bar.add(file);
		
		return bar;
	}
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { }
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});

	}

}
