package pt.uc.dei.nobugssnackbar.suporte;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	
	private String id;
	private String init;
	private String dest;
	
	private List<Order> orders = new ArrayList<>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInit() {
		return init;
	}
	public void setInit(String init) {
		this.init = init;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	@Override
	public String toString() {
		
		return id + " " + init + " " + dest + " " + orders.size() + " \n " + orders; 
	}
}

