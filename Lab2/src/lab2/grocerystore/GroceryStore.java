package lab2.grocerystore;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import lab2.grocerystore.dal.repositories.ItemRepository;
import lab2.grocerystore.models.Item;
import lab2.grocerystore.resources.Resources;
import lab2.grocerystore.resources.Resources.Locales;

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
		gridBagLayout.columnWidths = new int[] { 10, 0, 307, 10, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
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
		r.register(x -> tableItems.setModel(new ItemsTableModel()));
	}

	private class ItemsTableModel extends AbstractTableModel {
		private static final long serialVersionUID = -2851996699178618340L;

		String[] columnNames;

		Object[] data;

		private ItemsTableModel() {
			List<Item> all = new ItemRepository().getAll();

			columnNames = Arrays.stream(IntStream.range(0, 4).toArray()).boxed()
					.map(col -> Resources.get().getBundle(Resources.GUI_BUNDLE).getString("ItemsTable_Col_" + col))
					.toArray(String[]::new);

			data = all.stream().map(x -> new Object[] { x.getId(), x.getName(), x.getPricePerUnit(), x.getQuantity(),
					x.getTotalPrice() }).toArray();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public Object getValueAt(int row, int col) {
			return ((Object[]) data[row])[col];
		}

	}
}
