package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.Customer;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;
import pt.uc.dei.nobugssnackbar.suporte.Scripts;

public class GiveSomeChange extends Objective {

	public GiveSomeChange(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		
		int type = Integer.parseInt(conf.getMoneyType().substring(8));
		String typeM = (type > 2?"Notas":"Moedas");
		return String.format("Devolva ao cliente %d o troco em %s de %s.", this.conf.getPos() , typeM, type);
	}


	@Override
	protected boolean doVerifyObjective(Object options) {
		
		Customer cust = (Customer)options;
		
		try {
			Object value = Scripts.eval(conf.getQtd());
			int q;
			if (value.getClass() != Integer.class)
				q = (int) Math.round((double) value);
			else
				q = (int) value;
			return cust.getPos() == conf.getPos() && cust.isPaid() && cust.receivedChange(Integer.parseInt(conf.getMoneyType().substring(8)), q);
		} catch (Exception ex) {
			return false;
		}
	}

}
