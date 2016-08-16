package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class AskForIceCream extends ObjectiveVerifyCustomerPos {

	public AskForIceCream(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Pergunta por qual sabor de sorvete o cliente %d deseja comer.", conf.getPos());
	}

}
