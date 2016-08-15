package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class AskHasHunger extends ObjectiveVerifyCustomerPos {

	public AskHasHunger(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Pergunta se o cliente %d deseja comer algo.", conf.getPos());
	}


}
