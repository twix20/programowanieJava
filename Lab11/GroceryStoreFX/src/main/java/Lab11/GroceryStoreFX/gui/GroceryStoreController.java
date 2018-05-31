package Lab11.GroceryStoreFX.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class GroceryStoreController implements Initializable {
	
	private Stage primaryStage;
	
	@FXML
	private Label lblGroceriesTable;
	
	@FXML
	private MenuItem miEnglish;
	
	@FXML
	private MenuItem miPolish;
	
	
	@FXML
	public void miEnglishClick() {
		System.out.println("click english");
	}
	
	@FXML
	public void miPolishClick() {
		System.out.println("click");
	}

	
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("GroceryStoreController initialized");
		
	};
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
		
}
