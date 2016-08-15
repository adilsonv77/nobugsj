package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class AskHasThirsty extends ObjectiveVerifyCustomerPos {

	public AskHasThirsty(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Pergunta se o cliente %d deseja beber algo.", conf.getPos());
	}


}
