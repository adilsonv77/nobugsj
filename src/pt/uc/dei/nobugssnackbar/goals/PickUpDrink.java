package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.Customer;
import pt.uc.dei.nobugssnackbar.Order;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class PickUpDrink extends PickUpSomething {

	public PickUpDrink(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Pegue a bebida solicitada pelo cliente %d.", conf.getPos());
	}

	@Override
	protected Order askSomething(Customer cust) {
		return cust.askForDrink();
	}

}
