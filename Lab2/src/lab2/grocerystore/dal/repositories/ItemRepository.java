package lab2.grocerystore.dal.repositories;

import lab2.grocerystore.models.Item;

public class ItemRepository extends JsonRepository<Item> {

	public ItemRepository() {
		super("src/itemDatabase.json", Item.class);

	}

}
