package Lab11.GroceryStoreFX.gui;

import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Lab11.GroceryStoreFX.resources.Resources.SupportedLocale;
import Lab11.GroceryStoreFX.models.Item;
import Lab11.GroceryStoreFX.repositories.ItemRepository;
import Lab11.GroceryStoreFX.resources.Resources;

public class GroceryStoreController implements Initializable {
	
	private final String ItemsTablePrefix = "ItemsTable_";
	private ItemRepository itemRepository = new ItemRepository();
	
	@FXML
	private Label lblGroceriesTable;
	
	@FXML
	private MenuItem miEnglish;
	
	@FXML
	private MenuItem miPolish;
	
	@FXML
	private Menu menuLanguage;
	
	@FXML
	private TableView<GroceriesItem> tableGroceries;
	
	
	@FXML
	public void miEnglishClick() {
		Resources.get().changeLocale(SupportedLocale.English);
	}
	
	@FXML
	public void miPolishClick() {
		Resources.get().changeLocale(SupportedLocale.Polish);
	}

	
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("GroceryStoreController initialized");
		
		Resources r = Resources.get();
		
		r.register(menuLanguage, "Language", Resources.GUI_BUNDLE);
		r.register(miEnglish, "Language_English", Resources.GUI_BUNDLE);
		r.register(miPolish, "Language_Polish", Resources.GUI_BUNDLE);
		
		r.register(e -> updateTableItems());
		
	}
	
	private void updateTableItems() {
		
		Resources r = Resources.get();
		ResourceBundle rbGui = r.getBundle(Resources.GUI_BUNDLE);
		
		List<TableColumn<GroceriesItem, String>> columns = IntStream.rangeClosed(0, 4)
				.boxed()
				.map(i -> {
					TableColumn<GroceriesItem, String> col = new TableColumn<GroceriesItem, String>(rbGui.getString(ItemsTablePrefix + "Col_" + i));
					col.setCellValueFactory(new PropertyValueFactory<>("Col_" + i));
					
					return col;
				})
				.collect(Collectors.toList());
		   
		
		GroceriesItem[] data = itemRepository.getAll()
				.stream()
				.map(x -> new GroceriesItem (
						x.getId(),
						MessageFormat.format(Resources.get().getBundle(Resources.GROCERY_ITEMS_BUNDLE).getString(String.format("GroceryItem_%d_name_pattern", x.getId())), new Object[] {x.getQuantity()}), 
						Integer.toString(x.getQuantity()),
						r.localizeNumber(x.getPricePerUnit()), 
						String.format("%s PLN", Resources.get().localizeNumber(x.getTotalPrice())) ))
				.toArray(GroceriesItem[]::new);
		
		
		ObservableList<GroceriesItem> d = FXCollections.observableArrayList();
		d.addAll(data);
				
	    tableGroceries.getColumns().clear();
	    tableGroceries.getColumns().addAll(columns);
		
		tableGroceries.getItems().clear();
		tableGroceries.setItems(d);
		
		//Table label
		String pattern = rbGui.getString("lblItemsTable_pattern");
		long diffrentItemsCount = tableGroceries.getItems().stream().filter(item -> Integer.parseInt(item.getCol_3()) > 0).count();
		Object[] formatargs = { diffrentItemsCount };
		lblGroceriesTable.setText(MessageFormat.format(pattern, formatargs));
	}
	
	public class GroceriesItem {
		private int Col_0;
		private String Col_1;
		private String Col_2;
		private String Col_3;
		private String Col_4;
		
		public GroceriesItem(int id, String name, String cost, String quantity, String totalCost) {
			this.Col_0 = id;
			this.Col_1 = name;
			this.Col_2 = quantity;
			this.Col_3 = cost;
			this.Col_4 = totalCost;
		}
		
		public int getCol_0() {
			return Col_0;
		}

		public void setId(int id) {
			this.Col_0 = id;
		}

		public String getCol_1() {
			return Col_1;
		}

		public void setName(String name) {
			this.Col_1 = name;
		}

		public String getCol_2() {
			return Col_2;
		}

		public void setQuantity(String quantity) {
			this.Col_2 = quantity;
		}

		public String getCol_3() {
			return Col_3;
		}

		public void setCost(String cost) {
			this.Col_3 = cost;
		}

		public String getCol_4() {
			return Col_4;
		}

		public void setTotalCost(String totalCost) {
			this.Col_4 = totalCost;
		}

	}
		
}
