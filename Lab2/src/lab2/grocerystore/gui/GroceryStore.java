package lab2.grocerystore.gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import lab2.grocerystore.dal.repositories.ItemRepository;
import lab2.grocerystore.models.Item;
import lab2.grocerystore.resources.Resources;
import lab2.grocerystore.resources.Resources.SupportedLocale;

public class GroceryStore {

	private JFrame frmGroceryStore;
	private JTable tableItems;
	private JLabel lblAllItems;
	private JMenu mnLanguage;
	private JMenuItem mntmEnglish;
	private JMenuItem mntmPolish;
	private JScrollPane scrollPane;
	private ImagePanel panelItemImagePreview;

	private ItemRepository itemRepository = new ItemRepository();

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

		manageResources();

		Resources.get().changeLocale(SupportedLocale.English);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmGroceryStore = new JFrame();
		frmGroceryStore.setBounds(100, 100, 766, 339);
		frmGroceryStore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 15, 0, 160, 10, 0, 307, 0, 0, 10, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 30, 162, 0, 10, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		frmGroceryStore.getContentPane().setLayout(gridBagLayout);

		JMenuBar menuBar = new JMenuBar();
		frmGroceryStore.setJMenuBar(menuBar);

		mnLanguage = new JMenu();
		mnLanguage.setVerticalTextPosition(SwingConstants.TOP);
		mnLanguage.setText("Languages");
		menuBar.add(mnLanguage);

		mntmPolish = new JMenuItem();
		mntmPolish.setText("Polish");
		mntmPolish.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				Resources.get().changeLocale(SupportedLocale.Polish);
			}
		});
		mnLanguage.add(mntmPolish);

		mntmEnglish = new JMenuItem();
		mntmEnglish.setText("English");
		mntmEnglish.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Resources.get().changeLocale(SupportedLocale.English);
			}
		});
		mnLanguage.add(mntmEnglish);

		lblAllItems = new JLabel("All items");
		GridBagConstraints gbc_lblAllItems = new GridBagConstraints();
		gbc_lblAllItems.insets = new Insets(0, 0, 5, 5);
		gbc_lblAllItems.gridx = 4;
		gbc_lblAllItems.gridy = 0;
		frmGroceryStore.getContentPane().add(lblAllItems, gbc_lblAllItems);

		panelItemImagePreview = new ImagePanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		frmGroceryStore.getContentPane().add(panelItemImagePreview, gbc_panel);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 4;
		gbc_scrollPane.gridy = 1;
		frmGroceryStore.getContentPane().add(scrollPane, gbc_scrollPane);

		tableItems = new JTable();
		tableItems.setModel(new ItemsTableModel());
		tableItems.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				try {
					int selectedItemId = (int) tableItems.getValueAt(tableItems.getSelectedRow(), 0);

					updateItemPreviewPanel(selectedItemId);
				} catch (Exception e1) {
				}

			}
		});
		scrollPane.setViewportView(tableItems);

	}

	private void manageResources() {

		frmGroceryStore.setName("Frame_Title_groceryStore");
		mnLanguage.setName("Language");
		mntmPolish.setName("Language_Polish");
		mntmEnglish.setName("Language_English");

		Resources r = Resources.get();
		r.register(frmGroceryStore, Resources.GUI_BUNDLE);
		r.register(mnLanguage, Resources.GUI_BUNDLE);
		r.register(mntmPolish, Resources.GUI_BUNDLE);
		r.register(mntmEnglish, Resources.GUI_BUNDLE);
		r.register(x -> updateTableItems());

	}

	private void updateItemPreviewPanel(int itemId) {

		int width = 150;
		int height = 150;
		Image imageResized = null;
		try {
			File img = itemRepository.getImageFile(itemId);
			BufferedImage tmp = ImageIO.read(img);

			imageResized = tmp.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			this.panelItemImagePreview.setImage(imageResized);
			this.panelItemImagePreview.repaint();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void updateTableItems() {
		tableItems.setModel(new ItemsTableModel());

		// Update table label
		String pattern = Resources.get().getBundle(Resources.GUI_BUNDLE).getString("lblItemsTable_pattern");

		long diffrentItemsCount = Arrays.stream(((ItemsTableModel) tableItems.getModel()).data)
				.filter(x -> (int) ((Object[]) x)[3] > 0)
				.count();

		Object[] formatargs = { diffrentItemsCount };
		String result = MessageFormat.format(pattern, formatargs);

		lblAllItems.setText(result);

	}

	private class ItemsTableModel extends AbstractTableModel {
		private static final long serialVersionUID = -2851996699178618340L;

		String[] columnNames;

		Object[] data;

		private ItemsTableModel() {

			refreshData();
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

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 3;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (columnIndex != 3)
				return;

			int itemId = (int) getValueAt(rowIndex, 0);
			int newQuanity = Integer.parseInt(aValue.toString());

			itemRepository.update(itemId, newQuanity);

			updateTableItems();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 3)
				return Integer.class;

			return super.getColumnClass(columnIndex);
		}

		public void refreshData() {
			columnNames = Arrays.stream(IntStream.range(0, 5).toArray()).boxed()
					.map(col -> Resources.get().getBundle(Resources.GUI_BUNDLE).getString("ItemsTable_Col_" + col))
					.toArray(String[]::new);
			
			List<Item> all = itemRepository.getAll();
			data = all.stream()
					.map(x -> new Object[] { x.getId(),
							Resources.get().getBundle(Resources.GROCERY_ITEMS_BUNDLE)
									.getString(String.format("GroceryItem_%d_name", x.getId())),
							Resources.get().localizeNumber(x.getPricePerUnit()), x.getQuantity(),
							String.format("%s PLN", Resources.get().localizeNumber(x.getTotalPrice())) })
					.toArray();

			fireTableDataChanged();
		}

	}
}
