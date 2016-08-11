package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class AskForFood extends ObjectiveVerifyCustomerPos {

	public AskForFood(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Pergunta pelo que o cliente %d deseja comer.", conf.getPos());
	}

}
