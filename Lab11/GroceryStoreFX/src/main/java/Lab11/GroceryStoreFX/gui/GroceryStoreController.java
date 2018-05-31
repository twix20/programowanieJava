package Lab11.GroceryStoreFX.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.control.*;

import Lab11.GroceryStoreFX.resources.Resources.SupportedLocale;
import Lab11.GroceryStoreFX.resources.Resources;

public class GroceryStoreController implements Initializable {
	
	@FXML
	private Label lblGroceriesTable;
	
	@FXML
	private MenuItem miEnglish;
	
	@FXML
	private MenuItem miPolish;
	
	@FXML
	private Menu menuLanguage;
	
	
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
	};
		
}
