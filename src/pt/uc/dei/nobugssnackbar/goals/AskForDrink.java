package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class AskForDrink extends ObjectiveVerifyCustomerPos {

	public AskForDrink(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Pergunta pelo que o cliente %d deseja beber.", conf.getPos());
	}

}
