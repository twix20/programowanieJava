package Lab5.GenClassificator.GUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import Lab5.GenClassificator.Data.Database;
import Lab5.GenClassificator.Data.SqliteDbContext;
import Lab5.GenClassificator.Entities.Examined;
import Lab5.GenClassificator.Entities.Genotype;
import Lab5.KNearestNeighbours.NearestNeighbour;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

	private final String DB_PATH = "test.db";

	private JPanel contentPane;
	private JTable tableExamined;

	private Database db;
	private NearestNeighbour nnAlgorithm;
	private JTextField txtDbName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws URISyntaxException
	 * @throws SQLException
	 * @throws IOException
	 */
	public MainFrame() throws IOException, SQLException, URISyntaxException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 488, 367);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		TitledBorder border = new TitledBorder("Examined");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);

		JPanel panel = new JPanel();
		panel.setBorder(border);
		panel.setBounds(10, 11, 242, 274);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 222, 208);
		panel.add(scrollPane);

		tableExamined = new JTable();
		tableExamined.setModel(new ExaminedTableModel(new ArrayList<Examined>()));
		scrollPane.setViewportView(tableExamined);

		JButton btnNewButton = new JButton("Save To XML");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(db == null) return;
				
				try {
					db.dumpExaminedToXML("test.xml");
				} catch (FileNotFoundException | JAXBException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 240, 215, 23);
		panel.add(btnNewButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "1-NN Algorithm",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(262, 11, 199, 274);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 50, 179, 179);
		panel_1.add(scrollPane_1);

		JTextArea textAreaToClassify = new JTextArea();
		scrollPane_1.setViewportView(textAreaToClassify);

		JLabel lblGenotypesToClassify = new JLabel("Genotypes to classify");
		lblGenotypesToClassify.setBounds(10, 25, 171, 14);
		panel_1.add(lblGenotypesToClassify);

		JButton btnClassify = new JButton("Classify");
		btnClassify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(db == null) return;
				
				List<Genotype> genotypes = new ArrayList<>();
				try {
					genotypes = Arrays.stream(textAreaToClassify.getText().split("\\n"))
							
												.map(line -> new Genotype(line))
												.collect(Collectors.toList());
				} 
				catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(panel, "One of the genotypes is not valid", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				textAreaToClassify.setText("");
				
				List<Examined> classified = genotypes.stream().map(g -> new Examined(g.getGenotype(), nnAlgorithm.classifyGenotype(g))).collect(Collectors.toList());
				try {
					db.addOrUpdateExamined(classified);
					
					updateExaminedTable();
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		btnClassify.setBounds(10, 240, 179, 23);
		panel_1.add(btnClassify);
		
		txtDbName = new JTextField();
		txtDbName.setText("test.db");
		txtDbName.setBounds(20, 297, 117, 20);
		contentPane.add(txtDbName);
		txtDbName.setColumns(10);
		
		JButton btnConnectToDb = new JButton("Connect to Database");
		btnConnectToDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					db = new Database(new SqliteDbContext(DB_PATH));
					nnAlgorithm = new NearestNeighbour(db.getAllFlagellas(), db.getAllToughnesses());
					
					updateExaminedTable();
				} catch (IOException | SQLException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
		btnConnectToDb.setBounds(147, 296, 159, 23);
		contentPane.add(btnConnectToDb);
	}

	private void updateExaminedTable() throws SQLException, IOException {

		List<Examined> allExamined = db.getAllExamined();

		ExaminedTableModel m = ((ExaminedTableModel) tableExamined.getModel());
		m.setData(allExamined);
		m.fireTableDataChanged();
	}
}

class ExaminedTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 6555171111055993181L;

	private String[] columnNames = { "Genotype", "Class" };

	private List<Examined> data;

	public ExaminedTableModel(List<Examined> data) {
		this.setData(data);
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public int getRowCount() {
		if (data == null)
			return 0;

		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Examined e = data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return e.getGenotype();
		case 1:
			return e.getClazz();
		}
		return null;
	}

	public void setData(List<Examined> data) {
		this.data = data;
	}
}
