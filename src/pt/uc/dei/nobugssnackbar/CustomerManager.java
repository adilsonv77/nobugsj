package pt.uc.dei.nobugssnackbar;

import java.util.List;

public final class CustomerManager {
	
	private static List<Customer> _customers;

	public static int totalOfMoneyIfSell() {
		
		if (_customers == null)
			return 0;
		
		int total = 0;
		for (Customer c: _customers) {
			total += c.askHowMuchInFoodsIfSell() + c.askHowMuchInDrinksIfSell();	
		}
		return total;
	}

	public static int totalOfDrink() {
		
		if (_customers == null)
			return 0;
		
		int total = 0;
		for (Customer c: _customers) {
			total += c.askWantHowManyDrinks();	
		}
		return total;
	}
	
	public static int totalOfFood() {
		
		if (_customers == null)
			return 0;
		
		int total = 0;
		for (Customer c: _customers) {
			total += c.askWantHowManyFoods();	
		}
		return total;
	}
	
	public static int totalOfMoneyGave() {
		
		if (_customers == null)
			return 0;
	
		int ret = 0;
		for (Customer c: _customers) {
			if (c.isPaid())
				ret += c.getAmountPaid();
			else
				if (c.isThereIsACustomer())
					ret -= 100; // avoid cheating
			
		}
		
		return ret;
	}
	
	public static int customerMoneyGave(int custIdx) {
		
		if (_customers == null)
			return 0;
		
		if (_customers.get(custIdx-1).isPaid())
			return _customers.get(custIdx-1).getAmountPaid();
					
		return -100;
	}
	
	public static int customerMoneyIfSell(int custIdx) {
		if (_customers == null)
			return 0;
		
		Customer cust = _customers.get(custIdx-1);
		
		return cust.askHowMuchInFoodsIfSell() + cust.askHowMuchInDrinksIfSell();
	}
	
	public static int totalOfMoneyDelivered() {
		
		if (_customers == null)
			return 0;
		
		int ret = 0;
		for (Customer c: _customers) {
			ret += c.totalOfMoneyDelivered();
		}
		
		return ret;
	}
	
	static void setCustomers(List<Customer> customers) {

		_customers = customers;
	}

}
