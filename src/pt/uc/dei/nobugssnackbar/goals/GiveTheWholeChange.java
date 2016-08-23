package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.Customer;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class GiveTheWholeChange extends Objective {

	public GiveTheWholeChange(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Devolva ao cliente %d o troco integral e com o mínimo de notas e moedas.", this.conf.getPos());
	}


	@Override
	protected boolean doVerifyObjective(Object options) {
		
		Customer cust = (Customer)options;
		
		return (cust.getPos() == conf.getPos() && cust.isPaid() && cust.isChangeReceived() && cust.isTheBestChange());	}

}
