package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.Customer;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;
import pt.uc.dei.nobugssnackbar.suporte.Scripts;

public class CustomDeliver extends Objective {

	public CustomDeliver(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		
		if (conf.isFullText())
			return conf.getText();
		
		return String.format("Entregue somente %s ao cliente %1 quando ele pedir.", conf.getText(), conf.getPos());
	}

	@Override
	protected boolean doVerifyObjective(Object options) {
		Customer cust = (Customer) options;;

		try {
			Object typeOfItem = Scripts.eval(conf.getValue());
			
			if (typeOfItem == null) { // the customer must not received any item
				
				return (cust.getFoodDelivered() == 0) && (cust.getDrinksDelivered() == 0); // there arent any deliver 
			}
			
			String foodOrDrink = null;
			String sTypeOfItem = typeOfItem.toString();
			String itemId = sTypeOfItem;
			
			if (sTypeOfItem.indexOf("#") > 0) {
				String[] ti = sTypeOfItem.split("#");
				foodOrDrink = ti[0];
				itemId = ti[1];
			}
			
			return (cust.hasReceivedItem(foodOrDrink, itemId));
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
