package pt.uc.dei.nobugssnackbar.suporte;

import java.util.ArrayList;
import java.util.List;

public class Order {
	
	private List<OrderItem> foods = new ArrayList<>();
	private List<OrderItem> drinks = new ArrayList<>();
	
	private List<OrderItem> items = null;
	
	public void addFood(OrderItem food) {
		this.foods.add(food);
	}
	
	public void addDrink(OrderItem drink) {
		this.drinks.add(drink);
	}
	
	public List<OrderItem> getFoods() {
		return foods;
	}
	
	public List<OrderItem> getDrinks() {
		return drinks;
	}
	
	@Override
	public String toString() {
		
		return "Foods : " + foods + "\n Drinks : " + drinks + " \n\n ";
	}

	public List<OrderItem>  getItems() {
		
		if (items == null) {
			items = new ArrayList<>();
			items.addAll(foods);
			items.addAll(drinks);
		}
		
		return items;
	}

}
