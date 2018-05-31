package Lab11.GroceryStoreFX.repositories;

import java.io.File;
import java.util.List;

import Lab11.GroceryStoreFX.models.Item;


public class ItemRepository extends JsonRepository<Item> {
	public final String imageFolder = ItemRepository.class.getResource("").getPath(); //"src/lab2/grocerystore/gui/images/";
	
	public ItemRepository() {
		super(ItemRepository.class.getResource("itemDatabase.json").getPath(), Item.class);

	}
	
	public File getImageFile(int itemId) {
		return new File(imageFolder + "/" + itemId + ".png");
	}
	
	public void update(int itemId, int newQuantity) {
		List<Item> allItems = getAll();
		
		allItems.stream()
			.filter(x -> x.getId() == itemId)
			.forEach(x -> {
				//x.setName(updated.getName());
				//x.setPricePerUnit(updated.getPricePerUnit());
				x.setQuantity(newQuantity);
			});
		
		this.dumpToFile(allItems);
	}

}
