import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public class Main extends JFrame {
	
	private Vis mainPanel;
	
	public Main(){
		JMenuBar mb = setupMenu();
		setJMenuBar(mb);
		
		mainPanel = new Vis();
		setContentPane(mainPanel);
		
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Scatter Plot");
		setVisible(true);
	}
	
	public void executeQuery(String sql) {
		List<Float> param1 = new ArrayList<Float>();
		List<Float> param2 = new ArrayList<Float>();
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:derby:scatterData");
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				float p1 = rs.getFloat(1);
				float p2 = rs.getFloat(2);
				param1.add(p1);
				param2.add(p2);
			}
			
			mainPanel.setY(param1);
			mainPanel.setX(param2);
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private JMenuBar setupMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu query = new JMenu("Query");
		JMenuItem query1 = new JMenuItem("Credits attempted vs Credits passed");
		JMenuItem query2 = new JMenuItem("Credits attempted vs GPA");
		JMenuItem query3 = new JMenuItem("Credits passed vs GPA");
		JMenuItem query4 = new JMenuItem("Age vs GPA");
		JMenu plot = new JMenu("Plot");
		JMenuItem reset = new JMenuItem("Reset Plot");
		
		//queries
		query1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.clearLists();
				executeQuery("SELECT credits_passed, credits_attempted FROM data");
				mainPanel.setInitial(false);
				mainPanel.setStrings("Credits Passed", "Credits Attempted");
				repaint();
			}
		});
		
		query2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.clearLists();
				executeQuery("SELECT gpa, credits_attempted FROM data");
				mainPanel.setInitial(false);
				mainPanel.setStrings("GPA", "Credits Attempted");
				repaint();
			}
		});
		
		query3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.clearLists();
				executeQuery("SELECT gpa, credits_passed FROM data");
				mainPanel.setInitial(false);
				mainPanel.setStrings("GPA", "Credits Passed");
				repaint();
			}
		});
		
		query4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.clearLists();
				executeQuery("SELECT gpa, age FROM data");
				mainPanel.setInitial(false);
				mainPanel.setStrings("GPA", " Age");
				repaint();
			}
		});
		
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.setBoolMax(false);
				repaint();
			}
		});
		//string queries together
		query.add(query1);
		query.add(query2);
		query.add(query3);
		query.add(query4);
		plot.add(reset);
		menuBar.add(query);
		menuBar.add(plot);
		
		return menuBar;
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