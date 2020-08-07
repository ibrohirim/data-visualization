import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Main extends JFrame{

	private Vis mainPanel;
	private ArrayList<Axis> axes;
	
	public Main() {
		
		mainPanel = new Vis();
		setContentPane(mainPanel);
		
		JMenuBar mb = createMenu();
		setJMenuBar(mb);
		axes = new ArrayList<Axis>();
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Parallel Coordinates");
		setVisible(true);
	}
	
	private void dataBase(String db, String table) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:derby:"+db);
			PreparedStatement s = conn.prepareStatement("SELECT * FROM " + table, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = s.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
				String name = rsmd.getColumnLabel(i);
				String type = rsmd.getColumnTypeName(i);
				ArrayList<String> textData = new ArrayList<String>();
				ArrayList<Double> numData = new ArrayList<Double>();
				if(type == "DOUBLE") {
					while(rs.next()) {
						numData.add(rs.getDouble(i));
					}
					textData = null;
					axes.add(new Axis(name, textData, numData));
				} else {
					while(rs.next()) {
						String s1 = rs.getString(i);
						textData.add(s1);
					}
					numData = null;
					axes.add(new Axis(name, textData, numData));
				}
				rs.first();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private JMenuBar createMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem scatter = new JMenuItem("CIS 202");
		JMenuItem grad = new JMenuItem("CIS 2007");
		JMenuItem marathon = new JMenuItem("Marathon");
		
		scatter.addActionListener(e -> {
			if(!mainPanel.getAxes().isEmpty()) {
				mainPanel.clearAxes();
			}
			dataBase("scatterData","data");
			mainPanel.setAxes(axes);
			repaint();
		});
		grad.addActionListener(e -> {
			if(!mainPanel.getAxes().isEmpty()) {
				mainPanel.clearAxes();
			}
			dataBase("gradData", "data");
			mainPanel.setAxes(axes);
			repaint();
		});
		marathon.addActionListener(e -> {
			if(!mainPanel.getAxes().isEmpty()) {
				mainPanel.clearAxes();
			}
			dataBase("cs490R", "marathon");
			mainPanel.setAxes(axes);
			repaint();
		});
		
		file.add(scatter);
		file.add(grad);
		file.add(marathon);
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
