import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JPanel;

import Lab1.Core.MarkRange;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler.LegendPosition;

import com.bric.multislider.MultiThumbSlider;

import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
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
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JTextField;
import com.bric.multislider.MultiThumbSlider.Collision;

public class MainForm {

	final String WELCOME_PANEL = "Card welcome";
	final String MAIN_PANEL = "Card main";
	private JPanel welcomePanel;
	private JPanel mainPanel;

	private TestManager testManager;
	private PresentationManager presentationManager;
	private JFrame frmTestVisualizer;
	private JTable tableTests;
	private JTable tableStatistics;
	private JTextField txtMadeByPiotr;
	private MultiThumbSlider<Double> mtsMarksSlider;

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
				CardLayout cl = (CardLayout) frame.getLayout();
				cl.show(frame, MAIN_PANEL);
			}
		};
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestVisualizer = new BaseForm();
		frmTestVisualizer.setTitle("Test Visualizer");
		frmTestVisualizer.setBounds(100, 100, 1080, 268);
		frmTestVisualizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTestVisualizer.getContentPane().setLayout(new CardLayout(0, 0));

		welcomePanel = new JPanel();
		welcomePanel.setBackground(SystemColor.inactiveCaption);
		welcomePanel.addMouseListener(goToMainPanelEvent());
		frmTestVisualizer.getContentPane().add(welcomePanel, "name_122298456624859");
		GridBagLayout gbl_welcomePanel = new GridBagLayout();
		gbl_welcomePanel.columnWidths = new int[] { 256, 0, 0 };
		gbl_welcomePanel.rowHeights = new int[] { 140, 30, 0 };
		gbl_welcomePanel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_welcomePanel.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
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
		gbl_configurationPanel.columnWidths = new int[] { 15, 150, 199, 119, 10, 0 };
		gbl_configurationPanel.rowHeights = new int[] { 14, 121, 23, 10, 0 };
		gbl_configurationPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_configurationPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		configurationPanel.setLayout(gbl_configurationPanel);

		JButton btnAddTest = new JButton("Add Test");
		btnAddTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				FileDialog dialog = showChooseFile("Select Test to Open");
				if (dialog.getFile() == null)
					return;

				Path path = Paths.get(dialog.getDirectory(), dialog.getFile());
				testManager.loadTestFromFile(path.toString());

				TestTableModel model = (TestTableModel) tableTests.getModel();
				model.setTests(testManager.getTests());
				model.fireTableDataChanged();
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
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		configurationPanel.add(scrollPane, gbc_scrollPane);

		tableTests = new JTable();
		tableTests.setModel(new TestTableModel());
		tableTests.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = -3626538245723358222L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// TODO Auto-generated method stub
				setBackground(Color.WHITE);

				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (value.toString().contains("Not"))
					setBackground(Color.RED);

				return this;
			}
		});
		ListSelectionModel tableTestsModel = tableTests.getSelectionModel();
		scrollPane.setViewportView(tableTests);
		GridBagConstraints gbc_btnAddTest = new GridBagConstraints();
		gbc_btnAddTest.anchor = GridBagConstraints.NORTH;
		gbc_btnAddTest.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddTest.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddTest.gridx = 1;
		gbc_btnAddTest.gridy = 2;
		configurationPanel.add(btnAddTest, gbc_btnAddTest);

		JButton btnLoadAnswers = new JButton("Load Answers");
		btnLoadAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int selectedRow = tableTests.getSelectedRow();
				if (selectedRow == -1) {
					showError("Select the test from the list first");
					return;
				}
				Integer selectedTestId = (Integer) tableTests.getModel().getValueAt(selectedRow, 0);

				FileDialog dialog = showChooseFile("Select Answers to Open");
				if (dialog.getFile() == null)
					return;

				Path path = Paths.get(dialog.getDirectory(), dialog.getFile());
				testManager.loadAnswersFromFile(selectedTestId, path.toString());

				presentationManager.calculateForTest(testManager.getTestById(selectedTestId));

				refreshTableStatistics();
				tableTests.repaint();
			}
		});
		GridBagConstraints gbc_btnLoadAnswers = new GridBagConstraints();
		gbc_btnLoadAnswers.insets = new Insets(0, 0, 5, 5);
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
		gbl_panelStats.columnWidths = new int[] { 15, 251, 10, 168, 10, 0 };
		gbl_panelStats.rowHeights = new int[] { 7, 0, 129, 20, 0, 5, 0 };
		gbl_panelStats.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelStats.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelStats.setLayout(gbl_panelStats);
		presentationPanel.add(panelStats);

		JLabel lblStatisticsTable = new JLabel("Test aditional Informations:");
		GridBagConstraints gbc_lblStatisticsTable = new GridBagConstraints();
		gbc_lblStatisticsTable.anchor = GridBagConstraints.NORTH;
		gbc_lblStatisticsTable.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStatisticsTable.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatisticsTable.gridx = 1;
		gbc_lblStatisticsTable.gridy = 0;
		panelStats.add(lblStatisticsTable, gbc_lblStatisticsTable);
		lblStatisticsTable.setVerticalAlignment(SwingConstants.TOP);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 4;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		panelStats.add(scrollPane_1, gbc_scrollPane_1);

		tableStatistics = new JTable();
		scrollPane_1.setViewportView(tableStatistics);
		tableStatistics.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Information", "Value" }) {
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableStatistics.getColumnModel().getColumn(0).setMinWidth(15);

		JButton btnQuestionPassRateHistogram = new JButton("Question Pass Rate Histogram");
		btnQuestionPassRateHistogram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayChart("Question Pass Rate Histogram",
						() -> presentationManager.getTestResult().generateQuestionMarkRateHistogram());
			}
		});
		GridBagConstraints gbc_btnQuestionPassRateHistogram = new GridBagConstraints();
		gbc_btnQuestionPassRateHistogram.insets = new Insets(0, 0, 5, 5);
		gbc_btnQuestionPassRateHistogram.anchor = GridBagConstraints.NORTH;
		gbc_btnQuestionPassRateHistogram.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnQuestionPassRateHistogram.gridx = 3;
		gbc_btnQuestionPassRateHistogram.gridy = 1;
		panelStats.add(btnQuestionPassRateHistogram, gbc_btnQuestionPassRateHistogram);

		JButton btnMarkRateHistogram = new JButton("Mark Rate Histogram");
		btnMarkRateHistogram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayChart("Student Mark Rate Histogram",
					() -> presentationManager.getTestResult()
						.generateStudentMarkRateHistogram(() -> readMarkRanges())
				);
			}
		});
		GridBagConstraints gbc_btnMarkRateHistogram = new GridBagConstraints();
		gbc_btnMarkRateHistogram.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMarkRateHistogram.anchor = GridBagConstraints.NORTH;
		gbc_btnMarkRateHistogram.insets = new Insets(0, 0, 5, 5);
		gbc_btnMarkRateHistogram.gridx = 3;
		gbc_btnMarkRateHistogram.gridy = 2;
		panelStats.add(btnMarkRateHistogram, gbc_btnMarkRateHistogram);
		
		JLabel lblMarkRandeSlider = new JLabel("Mark range slider:");
		GridBagConstraints gbc_lblMarkRandeSlider = new GridBagConstraints();
		gbc_lblMarkRandeSlider.anchor = GridBagConstraints.WEST;
		gbc_lblMarkRandeSlider.insets = new Insets(0, 0, 5, 5);
		gbc_lblMarkRandeSlider.gridx = 3;
		gbc_lblMarkRandeSlider.gridy = 3;
		panelStats.add(lblMarkRandeSlider, gbc_lblMarkRandeSlider);
	
		mtsMarksSlider = new MultiThumbSlider(new float[] {0.5f, 0.6f, 0.67f, 0.75f, 0.85f, 0.93f}, new Object[] {0,0,0,0,0,0});
		mtsMarksSlider.setAutoAdding(false);
		mtsMarksSlider.setToolTipText("Grade Mark Slider");
		mtsMarksSlider.setThumbRemovalAllowed(false);
		mtsMarksSlider.setCollisionPolicy(Collision.STOP_AGAINST);
		mtsMarksSlider.setPaintTicks(true);
		GridBagConstraints gbc_mtsMarksSlider = new GridBagConstraints();
		gbc_mtsMarksSlider.insets = new Insets(0, 0, 5, 5);
		gbc_mtsMarksSlider.anchor = GridBagConstraints.NORTH;
		gbc_mtsMarksSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_mtsMarksSlider.gridx = 3;
		gbc_mtsMarksSlider.gridy = 4;
		panelStats.add(mtsMarksSlider, gbc_mtsMarksSlider);
		tableTestsModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!tableTestsModel.isSelectionEmpty()) {
					int selectedTestId = Integer
							.parseInt(tableTests.getValueAt(tableTests.getSelectedRow(), 0).toString());

					lblTest.setText("Test: " + selectedTestId);

					presentationManager.calculateForTest(testManager.getTestById(selectedTestId));
					refreshTableStatistics();
				}
			}
		});
	}

	private List<MarkRange> readMarkRanges(){
		
		float[] pos = mtsMarksSlider.getThumbPositions();
		
		double[] marks = new double[] {2.0, 3.0,3.5,4.0,4.5,5.0, 5.5};
		
		List<MarkRange> ranges = new ArrayList<>();
		
		for(int i = 0; i < pos.length; i++) {
			if(i == 0) {
				ranges.add(new MarkRange(0, pos[i], marks[i]));
			}
			else {
				MarkRange priorMark = ranges.get(i - 1);
				ranges.add(new MarkRange(priorMark.getTo() + 0.01, pos[i], marks[i]));
			}
		}
		
		MarkRange last = ranges.get(ranges.size() - 1);
		ranges.add(new MarkRange(last.getTo() + 0.01, 1, marks[pos.length]));
		
		return ranges;
	}
	
	private void displayChart(String frameTitle, Supplier<Chart> chartSupplier) {
		if (!presentationManager.canGenerateHistogram()) {
			showError("Load and choose test first");
			return;
		}

		Chart chart = chartSupplier.get();
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);

		XChartPanel<CategoryChart> panel = new XChartPanel(chart);

		// Display chart in new form
		JFrame chartFrame = new BaseForm(frameTitle);
		chartFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainForm.class.getResource("/Lab1/icon.jpg")));
		chartFrame.getContentPane().add(panel);
		chartFrame.pack();
		chartFrame.setVisible(true);
	}

	private FileDialog showChooseFile(String title) {
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
		if (r == null)
			return;

		List<Object[]> rows = new ArrayList<>();
		Test t = r.getTest();
		rows.add(new Object[] { "Id", t.getTestId() });
		rows.add(new Object[] { "Name", t.getTestName() });
		rows.add(new Object[] { "Questions", t.getQuestions().size() });
		rows.add(new Object[] { "All Question Options", t.getAllQuestionOptionsCount() });
		rows.add(new Object[] { "Easiest question", String.format("%d | %d", r.getEasiestQuestion().getQuestionId(),
				r.getEasiestQuestion().getStudentsAnsweredCorrectly()) });
		rows.add(new Object[] { "Hardest question", String.format("%d | %d", r.getHardesQuestion().getQuestionId(),
				r.getHardesQuestion().getStudentsAnsweredIncorrectly()) });
		rows.add(new Object[] { "Student Cards", t.getStudents().size() });

		// Best student
		// Worst student
		// Avarege points

		DefaultTableModel model = (DefaultTableModel) this.tableStatistics.getModel();
		model.setRowCount(0);
		for (Object[] row : rows)
			model.addRow(row);

	}

	static class TestTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 7223556055931136025L;
		
		public static final String[] columnNames = { "Id", "Name", "Questions", "Student Cards" };
		private List<Test> tests;

		public TestTableModel() {
			this.tests = new ArrayList<>();
		}

		public void setTests(List<Test> tests) {
			this.tests = tests;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
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
			return tests == null ? 0 : tests.size();
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
				return test.areAnswersLoaded() ? test.getStudents().size() : "Not loaded";
			}
			return null;
		}
	}
}
