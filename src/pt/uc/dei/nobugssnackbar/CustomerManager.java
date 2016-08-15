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

	static void setCustomers(List<Customer> customers) {

		_customers = customers;
	}

}
