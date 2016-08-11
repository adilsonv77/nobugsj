package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class GoesToCounter extends Objective {

	public GoesToCounter(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Vá para o balcão %d.", conf.getPos());
	}

	@Override
	protected boolean doVerifyObjective(Object options) {
		int i = (int) options;
		
		return i == conf.getPos();
	}

}
