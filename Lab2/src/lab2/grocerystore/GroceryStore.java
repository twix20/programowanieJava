package lab2.grocerystore;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import lab2.grocerystore.resources.Resources;
import lab2.grocerystore.resources.Resources.Locales;

import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class GroceryStore {

	private JFrame frmGroceryStore;
	private JTable tableItems;
	private Label lblAllItems;
	private JMenu mnLanguage;
	private JMenuItem mntmEnglish;
	private JMenuItem mntmPolish;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroceryStore window = new GroceryStore();
					window.frmGroceryStore.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public GroceryStore() {
		initialize();
		
		Resources.get().changeLocale(Locales.English);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmGroceryStore = new JFrame();
		frmGroceryStore.setBounds(100, 100, 583, 339);
		frmGroceryStore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 307, 10, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmGroceryStore.getContentPane().setLayout(gridBagLayout);
		
		lblAllItems = new Label();
		lblAllItems.setText("All items");
		GridBagConstraints gbc_lblAllItems = new GridBagConstraints();
		gbc_lblAllItems.insets = new Insets(0, 0, 5, 5);
		gbc_lblAllItems.gridx = 1;
		gbc_lblAllItems.gridy = 0;
		frmGroceryStore.getContentPane().add(lblAllItems, gbc_lblAllItems);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		frmGroceryStore.getContentPane().add(scrollPane, gbc_scrollPane);
		
		tableItems = new JTable();
		scrollPane.setViewportView(tableItems);
		tableItems.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		
		JMenuBar menuBar = new JMenuBar();
		frmGroceryStore.setJMenuBar(menuBar);
		
		mnLanguage = new JMenu();
		mnLanguage.setText("Languages");
		menuBar.add(mnLanguage);
		
		mntmPolish = new JMenuItem();
		mntmPolish.setText("Polish");
		mntmPolish.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				Resources.get().changeLocale(Locales.Polish);
			}
		});
		mnLanguage.add(mntmPolish);
		
		mntmEnglish = new JMenuItem();
		mntmEnglish.setText("English");
		mntmEnglish.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Resources.get().changeLocale(Locales.English);
			}
		});
		mnLanguage.add(mntmEnglish);
		
		frmGroceryStore.setName("Frame_Title_groceryStore");
		lblAllItems.setName("ItemsTable_Label");
		mnLanguage.setName("Language");
		mntmPolish.setName("Language_Polish");
		mntmEnglish.setName("Language_English");
		
		Resources r = Resources.get();
		r.register(frmGroceryStore, Resources.GUI_BUNDLE);
		r.register(lblAllItems, Resources.GUI_BUNDLE);
		r.register(mnLanguage, Resources.GUI_BUNDLE);
		r.register(mntmPolish, Resources.GUI_BUNDLE);
		r.register(mntmEnglish, Resources.GUI_BUNDLE);
	}
}
