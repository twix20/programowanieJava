import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JPanel;

import Lab1.Core.Test;
import Lab1.Core.TestManager;
import Lab1.Core.Presentation.PresentationManager;
import Lab1.Core.Presentation.PresentationResult;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.XChartPanel;

import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextPane;
import java.awt.Insets;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JTextField;

public class MainForm {
	
	final static String WELCOME_PANEL = "Card welcome";
	final static String MAIN_PANEL = "Card main";
	private JPanel welcomePanel;
	private JPanel mainPanel;

	private TestManager testManager;
	private PresentationManager presentationManager;
	private JFrame frmTestVisualizer;
	private JTable tableTests;
	private JTable tableStatistics;
	private JTextField txtMadeByPiotr;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmTestVisualizer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		testManager = new TestManager();
		presentationManager = new PresentationManager();

		initialize();
	}
	
	private MouseAdapter goToMainPanelEvent() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Container frame = frmTestVisualizer.getContentPane();
				CardLayout cl = (CardLayout)frame.getLayout();
				cl.show(frame, MAIN_PANEL);
			}
		};
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestVisualizer = new JFrame();
		frmTestVisualizer.getContentPane().setBackground(SystemColor.inactiveCaption);
		frmTestVisualizer.setBackground(SystemColor.inactiveCaption);
		frmTestVisualizer.setIconImage(Toolkit.getDefaultToolkit().getImage(MainForm.class.getResource("/Lab1/icon.jpg")));
		frmTestVisualizer.setTitle("Test Visualizer");
		frmTestVisualizer.setBounds(100, 100, 580, 268);
		frmTestVisualizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTestVisualizer.getContentPane().setLayout(new CardLayout(0, 0));
		
		welcomePanel = new JPanel();
		welcomePanel.setBackground(SystemColor.inactiveCaption);
		welcomePanel.addMouseListener(goToMainPanelEvent());
		frmTestVisualizer.getContentPane().add(welcomePanel, "name_122298456624859");
		GridBagLayout gbl_welcomePanel = new GridBagLayout();
		gbl_welcomePanel.columnWidths = new int[]{256, 0, 0};
		gbl_welcomePanel.rowHeights = new int[] {140, 30, 0};
		gbl_welcomePanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_welcomePanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		welcomePanel.setLayout(gbl_welcomePanel);
		
		JTextPane txtpnLabWelcomeTo = new JTextPane();
		txtpnLabWelcomeTo.addMouseListener(goToMainPanelEvent());
		txtpnLabWelcomeTo.setFont(new Font("Tahoma", Font.BOLD, 25));
		txtpnLabWelcomeTo.setEditable(false);
		txtpnLabWelcomeTo.setBackground(new Color(0, 0, 0, 0));
		txtpnLabWelcomeTo.setText("Lab1\r\n\r\nWelcome to Test Visualizer");
		GridBagConstraints gbc_txtpnLabWelcomeTo = new GridBagConstraints();
		gbc_txtpnLabWelcomeTo.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnLabWelcomeTo.gridx = 0;
		gbc_txtpnLabWelcomeTo.gridy = 0;
		welcomePanel.add(txtpnLabWelcomeTo, gbc_txtpnLabWelcomeTo);
		
		txtMadeByPiotr = new JTextField();
		txtMadeByPiotr.setEditable(false);
		txtMadeByPiotr.setText("Made by Piotr Markiewicz");
		GridBagConstraints gbc_txtMadeByPiotr = new GridBagConstraints();
		gbc_txtMadeByPiotr.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMadeByPiotr.gridx = 1;
		gbc_txtMadeByPiotr.gridy = 1;
		welcomePanel.add(txtMadeByPiotr, gbc_txtMadeByPiotr);
		txtMadeByPiotr.setColumns(10);
		
		mainPanel = new JPanel();
		frmTestVisualizer.getContentPane().add(mainPanel, "name_122291661539763");
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		mainPanel.add(tabbedPane);
		
		frmTestVisualizer.getContentPane().add(welcomePanel, this.WELCOME_PANEL);
		frmTestVisualizer.getContentPane().add(mainPanel, this.MAIN_PANEL);

		JPanel configurationPanel = new JPanel();
		tabbedPane.addTab("Configuration", null, configurationPanel, null);
		GridBagLayout gbl_configurationPanel = new GridBagLayout();
		gbl_configurationPanel.columnWidths = new int[]{0, 150, 199, 119, 0};
		gbl_configurationPanel.rowHeights = new int[]{14, 121, 23, 0};
		gbl_configurationPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_configurationPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		configurationPanel.setLayout(gbl_configurationPanel);
		
				JButton btnLoadTest = new JButton("Load Test");
				btnLoadTest.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						FileDialog dialog = shopChooseFile("Select Test to Open");
						
						Path path = Paths.get(dialog.getDirectory(), dialog.getFile());
						testManager.loadTestFromFile(path.toString());
						
						tableTests.setModel(new TestTableModel(testManager.getTests()));
					}
				});
				
						JLabel lblTest = new JLabel("Test:");
						GridBagConstraints gbc_lblTest = new GridBagConstraints();
						gbc_lblTest.anchor = GridBagConstraints.NORTHWEST;
						gbc_lblTest.insets = new Insets(0, 0, 5, 5);
						gbc_lblTest.gridx = 1;
						gbc_lblTest.gridy = 0;
						configurationPanel.add(lblTest, gbc_lblTest);
				
				JScrollPane scrollPane = new JScrollPane();
				GridBagConstraints gbc_scrollPane = new GridBagConstraints();
				gbc_scrollPane.fill = GridBagConstraints.BOTH;
				gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
				gbc_scrollPane.gridwidth = 3;
				gbc_scrollPane.gridx = 1;
				gbc_scrollPane.gridy = 1;
				configurationPanel.add(scrollPane, gbc_scrollPane);
				
						tableTests = new JTable();
						ListSelectionModel tableTestsModel = tableTests.getSelectionModel();
						scrollPane.setViewportView(tableTests);
						tableTests.setModel(new DefaultTableModel(
							new Object[][] {
							},
							TestTableModel.columnNames
						) {
							boolean[] columnEditables = new boolean[] {
								false, false, false, false
							};
							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						});
				GridBagConstraints gbc_btnLoadTest = new GridBagConstraints();
				gbc_btnLoadTest.anchor = GridBagConstraints.NORTH;
				gbc_btnLoadTest.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnLoadTest.insets = new Insets(0, 0, 0, 5);
				gbc_btnLoadTest.gridx = 1;
				gbc_btnLoadTest.gridy = 2;
				configurationPanel.add(btnLoadTest, gbc_btnLoadTest);
				
						JButton btnLoadAnswers = new JButton("Load Answers");
						btnLoadAnswers.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
								int selectedRow = tableTests.getSelectedRow();
								if(selectedRow == -1) {
									showError("Select the test from the list first");
									return;
								}
								Integer selectedTestId = (Integer)tableTests.getModel().getValueAt(selectedRow, 0);
								
								FileDialog dialog = shopChooseFile("Select Answers to Open");
								
								Path path = Paths.get(dialog.getDirectory(), dialog.getFile());
								testManager.loadAnswersFromFile(selectedTestId, path.toString());
								
								presentationManager.calculateForTest(testManager.getTestById(selectedTestId));
								
								refreshTableStatistics();
								tableTests.repaint();
							}
						});
						GridBagConstraints gbc_btnLoadAnswers = new GridBagConstraints();
						gbc_btnLoadAnswers.anchor = GridBagConstraints.NORTH;
						gbc_btnLoadAnswers.fill = GridBagConstraints.HORIZONTAL;
						gbc_btnLoadAnswers.gridx = 3;
						gbc_btnLoadAnswers.gridy = 2;
						configurationPanel.add(btnLoadAnswers, gbc_btnLoadAnswers);
				
						JPanel presentationPanel = new JPanel();
						tabbedPane.addTab("Presentation", null, presentationPanel, null);
						presentationPanel.setLayout(new GridLayout(0, 1, 0, 0));
						
						JPanel panelStats = new JPanel();
						GridBagLayout gbl_panelStats = new GridBagLayout();
						gbl_panelStats.columnWidths = new int[]{251, 168, 0};
						gbl_panelStats.rowHeights = new int[] {7, 159, 5, 0};
						gbl_panelStats.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
						gbl_panelStats.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
						panelStats.setLayout(gbl_panelStats);
						presentationPanel.add(panelStats);
						
						JLabel lblStatisticsTable = new JLabel("Test aditional Informations:");
						GridBagConstraints gbc_lblStatisticsTable = new GridBagConstraints();
						gbc_lblStatisticsTable.anchor = GridBagConstraints.NORTH;
						gbc_lblStatisticsTable.fill = GridBagConstraints.HORIZONTAL;
						gbc_lblStatisticsTable.insets = new Insets(0, 0, 5, 5);
						gbc_lblStatisticsTable.gridx = 0;
						gbc_lblStatisticsTable.gridy = 0;
						panelStats.add(lblStatisticsTable, gbc_lblStatisticsTable);
						lblStatisticsTable.setVerticalAlignment(SwingConstants.TOP);
						
						JButton btnHistogram = new JButton("Histogram");
						btnHistogram.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(!presentationManager.canGenerateHistogram()) {
									showError("Load and choose test first");
									return;
								}
								
								CategoryChart chart = presentationManager.generateHistogram();
								XChartPanel<CategoryChart> panel = new XChartPanel<CategoryChart>(chart); 

								//Display chart in new form
								JFrame chartFrame = new JFrame(String.format("%s presentation", panel.getChart().getTitle()));
								chartFrame.getContentPane().add(panel);
								chartFrame.pack();
								chartFrame.setVisible(true);
							}
						});
						
						JScrollPane scrollPane_1 = new JScrollPane();
						GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
						gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
						gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
						gbc_scrollPane_1.gridx = 0;
						gbc_scrollPane_1.gridy = 1;
						panelStats.add(scrollPane_1, gbc_scrollPane_1);
						
						tableStatistics = new JTable();
						scrollPane_1.setViewportView(tableStatistics);
						tableStatistics.setModel(new DefaultTableModel(
							new Object[][] {
							},
							new String[] {
								"Information", "Value"
							}
						) {
							boolean[] columnEditables = new boolean[] {
								false, false
							};
							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						});
						tableStatistics.getColumnModel().getColumn(0).setMinWidth(15);
						GridBagConstraints gbc_btnHistogram = new GridBagConstraints();
						gbc_btnHistogram.insets = new Insets(0, 0, 5, 0);
						gbc_btnHistogram.anchor = GridBagConstraints.NORTH;
						gbc_btnHistogram.fill = GridBagConstraints.HORIZONTAL;
						gbc_btnHistogram.gridx = 1;
						gbc_btnHistogram.gridy = 1;
						panelStats.add(btnHistogram, gbc_btnHistogram);
		tableTestsModel.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(!tableTestsModel.isSelectionEmpty()) {
					int selectedTestId = Integer.parseInt(tableTests.getValueAt(tableTests.getSelectedRow(), 0).toString());
					
					lblTest.setText("Test: " + selectedTestId);
					
					presentationManager.calculateForTest(testManager.getTestById(selectedTestId));
					refreshTableStatistics();
				}
			}
		});
	}
	
	private FileDialog shopChooseFile(String title) {
		FileDialog dialog = new FileDialog((Frame) null, title);
		dialog.setMode(FileDialog.LOAD);
		dialog.setVisible(true);
		
		return dialog;
	}
	
	private void showError(String infoMessage) {
		String title = "Error";
		JOptionPane.showMessageDialog(null, infoMessage, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void refreshTableStatistics() {
		PresentationResult r = presentationManager.getTestResult();
		if(r == null) 
			return;

		List<Object[]> rows = new ArrayList<>();
		Test t = r.getTest();
		rows.add(new Object[] {"Id", t.getTestId()});
		rows.add(new Object[] {"Name", t.getTestName()});
		rows.add(new Object[] {"Questions", t.getQuestions().size()});
		rows.add(new Object[] {"Easiest question", String.format("%d | %d", r.getEasiestQuestion().getQuestionId(), r.getEasiestQuestion().getStudentsAnsweredCorrectly())});
		rows.add(new Object[] {"Hardest question", String.format("%d | %d", r.getHardesQuestion().getQuestionId(), r.getHardesQuestion().getStudentsAnsweredIncorrectly())});
		
		//Best student
		//Worst student
		//Avarege points
		
		DefaultTableModel model = (DefaultTableModel)this.tableStatistics.getModel();
		model.setRowCount(0);
		for(Object[] row: rows)
			model.addRow(row);

	}
	
	static class TestTableModel extends AbstractTableModel {
		public static final String[] columnNames = {"Id", "Name", "Questions", "Question Answers", "Students"};
		private List<Test> tests;
		
		public TestTableModel(List<Test> tests) {
			this.tests = tests;
		}
		
		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}
		
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return tests.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			
			Test test = tests.get(rowIndex);
			switch (columnIndex) {
				case 0:
					return test.getTestId();
				case 1:
					return test.getTestName();
				case 2:
					return test.getQuestions().size();
				case 3:
					return test.getAnswersCount();
				case 4:
					return test.areAnswersLoaded() ? test.getStudents().size() : "Not loaded";
			}
			return null;
		}
	}
}
