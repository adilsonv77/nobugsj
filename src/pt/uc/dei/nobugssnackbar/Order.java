package pt.uc.dei.nobugssnackbar;

public class Order {

	public static final Order CHOCOLATE = new Order("item", "$$icecreamofchocolate", "food", 0, null);

	public static final Order REFRIGERANTE = new Order("item", "$$coke", "drink", 0, null);
	public static final Order SUCODELARANJA = new Order("item", "$$juiceoforange", "drink", 0, null);
	
	public static final Order CAFE = new Order("item", "$$coffee", "drink", 0, null);
	
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
		
		return item;
	}

	@Override
	public boolean equals(Object obj) {
		Order o = (Order) obj;
		
		return o.item.equals(item);
	}
	
	void delivered() {
		// Cancel this object
		
		this.typeOrder = null;
	}
	
}
