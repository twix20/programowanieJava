package Lab11.GroceryStoreFX;

import Lab11.GroceryStoreFX.gui.GroceryStoreController;
import Lab11.GroceryStoreFX.resources.Resources;
import Lab11.GroceryStoreFX.resources.Resources.SupportedLocale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    GroceryStoreController groceryStoreController;
	
    public static void main( String[] args )
    {
    	launch(args);
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GroceryStoreController.class.getResource("GroceryStoreController.fxml"));
        
        Parent root = (Parent) loader.load();
    	groceryStoreController = loader.getController();
    	
    	
    	Resources.get().changeLocale(SupportedLocale.English);
    	
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
