package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.Customer;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class Deliver extends Objective {

	public Deliver(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Entregue o pedido do cliente %d.", this.conf.getPos());
	}


	@Override
	protected boolean doVerifyObjective(Object options) {
		Customer cust = (Customer) options;
		
		return !(cust.hasThirsty() || cust.hasHunger());
	}

}
