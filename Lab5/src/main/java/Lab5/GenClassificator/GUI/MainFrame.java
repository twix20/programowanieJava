package Lab5.GenClassificator.GUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private final String DB_PATH = "test.db";
	
	private JPanel contentPane;
	private JTable tableExamined;
	
	private Database db;

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
	 * @throws URISyntaxException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public MainFrame() throws IOException, SQLException, URISyntaxException {
		db = new Database(new SqliteDbContext(DB_PATH));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TitledBorder border = new TitledBorder("Examined");
	    border.setTitleJustification(TitledBorder.LEFT);
	    border.setTitlePosition(TitledBorder.TOP);
		
		JPanel panel = new JPanel();
		panel.setBorder(border);
		panel.setBounds(10, 11, 505, 274);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 485, 202);
		panel.add(scrollPane);
		
		tableExamined = new JTable();
		tableExamined.setModel(new ExaminedTableModel(new ArrayList<Examined>()));
		scrollPane.setViewportView(tableExamined);
		
		JButton btnNewButton = new JButton("Save To XML");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					db.dumpExaminedToXML("test.xml");
				} catch (FileNotFoundException | JAXBException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(296, 234, 199, 23);
		panel.add(btnNewButton);
		
		updateExaminedTable();
	}
	
	private void updateExaminedTable() throws SQLException, IOException {

		List<Examined> allExamined = db.getAllExamined();
		
		ExaminedTableModel m = ((ExaminedTableModel)tableExamined.getModel());
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
    public String getColumnName(int columnIndex){
         return columnNames[columnIndex];
    }

    @Override     
    public int getRowCount() {
    	if(data == null)
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
