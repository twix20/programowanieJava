package lab2.grocerystore.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import lab2.grocerystore.dal.repositories.ItemRepository;
import lab2.grocerystore.models.Item;
import lab2.grocerystore.resources.Resources;
import lab2.grocerystore.resources.Resources.Locales;
import javax.swing.SwingConstants;

public class GroceryStore {

	private JFrame frmGroceryStore;
	private JTable tableItems;
	private Label lblAllItems;
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

		Resources.get().changeLocale(Locales.English);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmGroceryStore = new JFrame();
		frmGroceryStore.setBounds(100, 100, 766, 339);
		frmGroceryStore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 15, 0, 160, 0, 307, 0, 0, 10, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 162, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
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

		//region Managing Resources
		
		frmGroceryStore.setName("Frame_Title_groceryStore");
		mnLanguage.setName("Language");
		mntmPolish.setName("Language_Polish");
		mntmEnglish.setName("Language_English");

		Resources r = Resources.get();
		r.register(frmGroceryStore, Resources.GUI_BUNDLE);
				
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
				gbc_scrollPane.gridx = 3;
				gbc_scrollPane.gridy = 1;
				frmGroceryStore.getContentPane().add(scrollPane, gbc_scrollPane);
				
						tableItems = new JTable();
						tableItems.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							
							@Override
							public void valueChanged(ListSelectionEvent e) {
								
								try {
									int selectedItemId = (int)tableItems.getValueAt(tableItems.getSelectedRow(), 0);
									
									updateItemPreviewPanel(selectedItemId);
								} catch (Exception e1) {}

							}
						});
						scrollPane.setViewportView(tableItems);
						
								lblAllItems = new Label();
								scrollPane.setColumnHeaderView(lblAllItems);
								lblAllItems.setText("All items");
								lblAllItems.setName("ItemsTable_Label");
								r.register(lblAllItems, Resources.GUI_BUNDLE);
		r.register(mnLanguage, Resources.GUI_BUNDLE);
		r.register(mntmPolish, Resources.GUI_BUNDLE);
		r.register(mntmEnglish, Resources.GUI_BUNDLE);
		r.register(x -> updateTableItems());
		
		//endregion
		
	}
	
	private void updateItemPreviewPanel(int itemId) {
		
		int width = 150;
		int height = 150;
		try {
			File img = itemRepository.getImageFile(itemId);
			BufferedImage tmp = ImageIO.read(img);
			
		    Image imageResized = tmp.getScaledInstance(width, height, Image.SCALE_SMOOTH);

			this.panelItemImagePreview.setImage(imageResized);
			this.panelItemImagePreview.repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	
	private void updateTableItems() {
		tableItems.setModel(new ItemsTableModel());
	}

	private class ItemsTableModel extends AbstractTableModel {
		private static final long serialVersionUID = -2851996699178618340L;

		String[] columnNames;

		Object[] data;

		private ItemsTableModel() {
			List<Item> all = itemRepository.getAll();

			columnNames = Arrays.stream(IntStream.range(0, 4).toArray()).boxed()
					.map(col -> Resources.get().getBundle(Resources.GUI_BUNDLE).getString("ItemsTable_Col_" + col))
					.toArray(String[]::new);

			data = all.stream()
					.map(x -> new Object[] { x.getId(), x.getName(), x.getPricePerUnit(), x.getQuantity(),x.getTotalPrice() })
					.toArray();
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
