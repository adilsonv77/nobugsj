package pt.uc.dei.nobugssnackbar;

public class Order {

	private String typeOrder;
	private String item;
	private String foodOrDrink;
	private int custPosition;
	private String custPlace;

	Order(String typeOrder, String item, String foodOrDrink, int custPosition, String custPlace) {
		this.typeOrder = typeOrder;
		this.item = item;
		this.foodOrDrink = foodOrDrink;
		this.custPosition = custPosition+1;
		this.custPlace = custPlace;
	}

	public String getTypeOrder() {
		return typeOrder;
	}

	public String getItem() {
		return item;
	}

	public String getFoodOrDrink() {
		return foodOrDrink;
	}

	public int getCustPosition() {
		return custPosition;
	}

	public String getCustPlace() {
		return custPlace;
	}
	
	@Override
	public String toString() {
		
		return typeOrder + "  " + item + " " + custPosition;
	}
	
}
