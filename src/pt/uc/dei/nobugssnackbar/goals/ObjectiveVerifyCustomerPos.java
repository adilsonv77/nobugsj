package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.Customer;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public abstract class ObjectiveVerifyCustomerPos extends Objective {

	public ObjectiveVerifyCustomerPos(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	protected boolean doVerifyObjective(Object options) {
		Customer cust = (Customer) options;
		
		return cust.getPos() == conf.getPos();
	}

}
