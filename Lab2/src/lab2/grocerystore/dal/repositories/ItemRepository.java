package lab2.grocerystore.dal.repositories;

import java.io.File;

import lab2.grocerystore.models.Item;

public class ItemRepository extends JsonRepository<Item> {
	public final String imageFolder = "src/lab2/images/";
	
	public ItemRepository() {
		super("src/itemDatabase.json", Item.class);

	}
	
	public File getImageFile(int itemId) {
		return new File(imageFolder + itemId + ".png");
	}

}
