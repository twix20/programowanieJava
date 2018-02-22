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

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class MainForm {

	private TestManager testManager;
	private PresentationManager presentationManager;
	private JFrame frmTestVisualizer;
	private JTable tableTests;

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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestVisualizer = new JFrame();
		frmTestVisualizer.setTitle("Test Visualizer");
		frmTestVisualizer.setBounds(100, 100, 580, 251);
		frmTestVisualizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTestVisualizer.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmTestVisualizer.getContentPane().add(tabbedPane);

		JPanel configurationPanel = new JPanel();
		tabbedPane.addTab("Configuration", null, configurationPanel, null);
		configurationPanel.setLayout(null);

		JButton btnLoadTest = new JButton("Load Test");
		btnLoadTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				FileDialog dialog = shopChooseFile("Select Test to Open");
				
				Path path = Paths.get(dialog.getDirectory(), dialog.getFile());
				testManager.loadTestFromFile(path.toString());
				
				tableTests.setModel(new TestTableModel(testManager.getTests()));
			}
		});
		btnLoadTest.setBounds(20, 148, 150, 23);
		configurationPanel.add(btnLoadTest);

		JLabel lblTest = new JLabel("Test:");
		lblTest.setBounds(20, 10, 46, 14);
		configurationPanel.add(lblTest);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 35, 517, 102);
		configurationPanel.add(scrollPane);

		tableTests = new JTable();
		ListSelectionModel tableTestsModel = tableTests.getSelectionModel();
		tableTestsModel.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(!tableTestsModel.isSelectionEmpty()) {
					int selectedTestId = Integer.parseInt(tableTests.getValueAt(tableTests.getSelectedRow(), 0).toString());
					
					lblTest.setText("Test: " + selectedTestId);
					
					presentationManager.calculateForTest(testManager.getTestById(selectedTestId));
				}
			}
		});
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
				
				tableTests.repaint();
				
				presentationManager.calculateForTest(testManager.getTestById(selectedTestId));
			}
		});
		btnLoadAnswers.setBounds(418, 148, 119, 23);
		configurationPanel.add(btnLoadAnswers);

		JPanel presentationPanel = new JPanel();
		tabbedPane.addTab("Presentation", null, presentationPanel, null);
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
	
	static class TestTableModel extends AbstractTableModel {
		public static String[] columnNames = {"Id", "Name", "Questions", "Question Answers", "Student Answers"};
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
					return test.getStudentAnswers().size() == 0 ? "Not loaded" : test.getStudentAnswers().size();
			}
			return null;
		}

	}
}
