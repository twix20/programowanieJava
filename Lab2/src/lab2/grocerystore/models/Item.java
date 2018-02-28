package lab2.grocerystore.models;

import java.math.BigDecimal;

public class Item {
	private int id;
	private String name;
	private BigDecimal pricePerUnit;
	private int quantity;
	
	public BigDecimal getTotalPrice() {
		return pricePerUnit.multiply(new BigDecimal(quantity));
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
