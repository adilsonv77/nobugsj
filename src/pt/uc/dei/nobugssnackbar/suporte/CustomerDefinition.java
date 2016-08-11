package pt.uc.dei.nobugssnackbar.suporte;

import java.util.ArrayList;
import java.util.List;

public class CustomerDefinition {
	
	private String id;
	private String init;
	private String dest;
	
	private List<OrderConf> orders = new ArrayList<>();
	
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
	
	public void addOrder(OrderConf order) {
		this.orders.add(order);
	}
	
	public List<OrderConf> getOrders() {
		return orders;
	}
	
	@Override
	public String toString() {
		
		return id + " " + init + " " + dest + " " + orders.size() + " \n " + orders; 
	}
	public List<OrderConf> cloneOrders() {
		
		List<OrderConf> ret = new ArrayList<>();
		
		for (OrderConf o:orders) {
			OrderConf no = new OrderConf();
			
			for (OrderItem d:o.getDrinks())
				no.addDrink(d.clone());
			
			for (OrderItem d:o.getFoods())
				no.addFood(d.clone());
			
			ret.add(no);
		}
		
		return ret;
	}
}

