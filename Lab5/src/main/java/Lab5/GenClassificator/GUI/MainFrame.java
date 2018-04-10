package Lab5.GenClassificator.GUI;

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

import Lab5.GenClassificator.Data.SqliteDbContext;
import Lab5.GenClassificator.Entities.Examined;

public class MainFrame extends JFrame {

	private final String DB_NAME = "test.db";
	
	private JPanel contentPane;
	private JTable table;
	
	private SqliteDbContext dbContext;

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
		dbContext = new SqliteDbContext(DB_NAME);
		
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
		panel.setBounds(10, 11, 505, 234);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 485, 202);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setModel(new ExaminedTableModel(new ArrayList<Examined>()));
		scrollPane.setViewportView(table);
	}

	public SqliteDbContext getDbContext() {
		return dbContext;
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

	public List<Examined> getData() {
		return data;
	}

	public void setData(List<Examined> data) {
		this.data = data;
	}
}
