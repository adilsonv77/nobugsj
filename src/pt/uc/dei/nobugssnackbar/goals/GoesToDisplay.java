package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class GoesToDisplay extends Objective {

	public GoesToDisplay(ObjectiveConf conf) {
		super(conf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getText() {
		return "V� para o mostrador quente.";
	}

	@Override
	protected boolean doVerifyObjective(Object options) {
		return true;
	}

}
