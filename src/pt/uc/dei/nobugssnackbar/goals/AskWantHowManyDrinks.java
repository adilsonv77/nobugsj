package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class AskWantHowManyDrinks extends ObjectiveVerifyCustomerPos {

	public AskWantHowManyDrinks(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Pergunta ao cliente %d quantas bebidas ele deseja.", conf.getPos());
	}

}
