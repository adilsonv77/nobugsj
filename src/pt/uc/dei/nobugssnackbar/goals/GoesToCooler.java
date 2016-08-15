package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class GoesToCooler extends Objective {

	public GoesToCooler(ObjectiveConf conf) {
		super(conf);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getText() {
		return "Vá para o refrigerador.";
	}

	@Override
	protected boolean doVerifyObjective(Object options) {
		return true;
	}

}
