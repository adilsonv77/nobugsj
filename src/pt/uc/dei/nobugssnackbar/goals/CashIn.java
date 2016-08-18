package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.Customer;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class CashIn extends Objective {

	public CashIn(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Cobrar o valor do cliente %d.", this.conf.getPos());
	}


	@Override
	protected boolean doVerifyObjective(Object options) {
		
		Customer cust = (Customer)options;
		
		return cust.getPos() == conf.getPos() && cust.isPaid();
	}

}
