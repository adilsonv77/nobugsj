package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class AskWantHowManyFoods extends ObjectiveVerifyCustomerPos {

	public AskWantHowManyFoods(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Pergunta ao cliente %d quantos lanches ele deseja comer.", conf.getPos());
	}

}
