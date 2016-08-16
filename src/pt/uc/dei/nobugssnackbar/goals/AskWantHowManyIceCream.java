package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class AskWantHowManyIceCream extends ObjectiveVerifyCustomerPos {

	public AskWantHowManyIceCream(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Pergunta ao cliente %d quantos sorvetes ele deseja comer.", conf.getPos());
	}

}
