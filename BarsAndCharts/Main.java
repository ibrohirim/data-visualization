import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public class Main extends JFrame{
	
	private Vis mainPanel;
	
	public Main() {
		
		JMenuBar mb = setupMenu();
		setJMenuBar(mb);
		
		mainPanel = new Vis();
		setContentPane(mainPanel);
		
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Bars/Charts");
		setVisible(true);
	}
	
	private JMenuBar setupMenu() {
		//instantiate jmenu and items
		JMenuBar menuBar = new JMenuBar();
		JMenu queryMenu = new JMenu("Query");
		JMenuItem query1 = new JMenuItem("# of students in each major");
		JMenuItem query2 = new JMenuItem("# of students from each home area");
		JMenuItem query3 = new JMenuItem("Average GPA of students in each major");
		JMenuItem query4 = new JMenuItem("Average number of credits attempted per year");
		JMenuItem query5 = new JMenuItem("Number of students per GPA");
		JMenu chartType = new JMenu("Chart Type");
		JCheckBoxMenuItem bar = new JCheckBoxMenuItem("Bar", true);
		JCheckBoxMenuItem line = new JCheckBoxMenuItem("Line");
		
		//queries to be run for each item
		query1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Float> counts = new ArrayList<Float>();
				List<String> majors = new ArrayList<String>();
				try {
					Connection conn = DriverManager.getConnection("jdbc:derby:gradData");
					Statement s = conn.createStatement();
					ResultSet rs = s.executeQuery("SELECT COUNT(*), major FROM data GROUP BY major");
					while(rs.next()) {
						float count = rs.getFloat(1);
						String major = rs.getString(2);
						counts.add(count);
						majors.add(major);
					}
					mainPanel.clearLists();
					mainPanel.setCounts(counts);
					mainPanel.setStrings(majors);
					mainPanel.setInitial(false);
					repaint();
					conn.close();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		query2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Float> counts = new ArrayList<Float>();
				List<String> region = new ArrayList<String>();
				try {
					Connection conn = DriverManager.getConnection("jdbc:derby:gradData");
					Statement s = conn.createStatement();
					ResultSet rs = s.executeQuery("SELECT COUNT(*), region FROM data GROUP BY region");
					while(rs.next()) {
						float count = rs.getFloat(1);
						String major = rs.getString(2);
						counts.add(count);
						region.add(major);
					}
					
					mainPanel.clearLists();
					mainPanel.setCounts(counts);
					mainPanel.setStrings(region);
					mainPanel.setInitial(false);
					repaint();
					conn.close();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		query3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Float> counts = new ArrayList<Float>();
				List<String> majors = new ArrayList<String>();
				try {
					Connection conn = DriverManager.getConnection("jdbc:derby:gradData");
					Statement s = conn.createStatement();
					ResultSet rs = s.executeQuery("SELECT AVG(gpa), major FROM data GROUP BY major");
					while(rs.next()) {
						float count = rs.getFloat(1);
						String major = rs.getString(2);
						counts.add(count);
						majors.add(major);
					}
					
					mainPanel.clearLists();
					mainPanel.setCounts(counts);
					mainPanel.setStrings(majors);
					mainPanel.setInitial(false);
					repaint();
					conn.close();
					
				} catch (SQLException e1){
					e1.printStackTrace();
				}
			}
		});
		
		query4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Float> counts = new ArrayList<Float>();
				List<String> majors = new ArrayList<String>();
				try {
					Connection conn = DriverManager.getConnection("jdbc:derby:gradData");
					Statement s = conn.createStatement();
					ResultSet rs = s.executeQuery("SELECT AVG(credits_attempted), grad_year FROM data GROUP BY grad_year");
					while(rs.next()) {
						float count = rs.getFloat(1);
						String major = Integer.toString(rs.getInt(2));
						counts.add(count);
						majors.add(major);
					}
					
					mainPanel.clearLists();
					mainPanel.setCounts(counts);
					mainPanel.setStrings(majors);
					mainPanel.setInitial(false);
					repaint();
					conn.close();
					
				} catch (SQLException e1){
					e1.printStackTrace();
				}
			}
			
		});
		
		query5.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Float> counts = new ArrayList<Float>();
				List<String> ranges = new ArrayList<String>();
				try {
					Connection conn = DriverManager.getConnection("jdbc:derby:gradData");
					Statement s = conn.createStatement();
					ResultSet rs = s.executeQuery("SELECT COUNT(*), t.range FROM (select case "
							+ "when gpa >= 2.25 AND gpa <= 2.5 then '2.25-2.50' "
							+ "when gpa >= 2.5 AND gpa <= 2.75 then '2.50-2.75' "
							+ "when gpa >= 2.75 AND gpa <= 3.0 then '2.75-3.00' "
							+ "when gpa >= 3.0 AND gpa <= 3.25 then '3.00-3.25' "
							+ "when gpa >= 3.25 AND gpa <= 3.5 then '3.25-3.50' "
							+ "when gpa >= 3.5 AND gpa <= 3.75 then '3.50-3.75' "
							+ "when gpa >= 3.75 AND gpa <= 4.0 then '3.75-4.00' "
							+ "end "
							+ "as range "
							+ "from data) as t "
							+ "GROUP BY range");
					while(rs.next()) {
						float count = rs.getFloat(1);
						String range = rs.getString(2);
						counts.add(count);
						ranges.add(range);
					}
					mainPanel.clearLists();
					mainPanel.setCounts(counts);
					mainPanel.setStrings(ranges);
					mainPanel.setInitial(false);
					repaint();
					conn.close();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//switch between bar and line charts		
		bar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.setBar(true);
				mainPanel.setLine(false);
				line.setSelected(false);
				repaint();
			}
		});
		
		line.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.setLine(true);
				mainPanel.setBar(false);
				bar.setSelected(false);
				repaint();
			}
		});
		//string togethert the menubar
		queryMenu.add(query1);
		queryMenu.add(query2);
		queryMenu.add(query3);
		queryMenu.add(query4);
		queryMenu.add(query5);
		chartType.add(bar);
		chartType.add(line);
		menuBar.add(queryMenu);
		menuBar.add(chartType);
		
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
