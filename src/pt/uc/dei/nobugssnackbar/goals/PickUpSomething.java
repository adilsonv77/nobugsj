package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.Customer;
import pt.uc.dei.nobugssnackbar.Order;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public abstract class PickUpSomething extends Objective {


	public PickUpSomething(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	protected boolean doVerifyObjective(Object options) {
		Order o = (Order) options;
		Customer cust = null;
		if (o.getCustPlace().equals(conf.getPlace())) {
			cust = getMundo().getCustomerCounter(o.getCustPosition());
		}
		if (cust == null)
			return false;
			
		Order something = this.askSomething(cust);
		
		return something != null && something.getItem().equals(o.getItem());
	}

	protected abstract Order askSomething(Customer cust);


}
