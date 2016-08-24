package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.Customer;
import pt.uc.dei.nobugssnackbar.Order;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;
import pt.uc.dei.nobugssnackbar.suporte.Scripts;

public class DeliverGifts extends Objective {

	public DeliverGifts(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Entregue um presente (%s) ao cliente %d.", conf.getGift(), conf.getPos());
	}

	@Override
	protected boolean doVerifyObjective(Object options) {
		
		try {
			Customer cust = (Customer) options;
			Object typeOfGift = Scripts.eval(conf.getValue());
			if (typeOfGift.toString().length() == 0) { // if he must not receive a gift
				for (Order d:cust.getDeliveredItems())
					if (d.getCustPlace() == null)
						return false;
				return true; // is true if the customer didnt received
			}
				
			return (cust.hasReceivedGift(typeOfGift.toString()));
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
