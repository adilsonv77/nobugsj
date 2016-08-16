package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class DeliverGifts extends Objective {

	public DeliverGifts(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Entregue um presente (%s) ao cliente %d.", conf.getGift(), conf.getPos());
	}

	@Override
	protected boolean doVerifyObjective(Object options) {
		// TODO Auto-generated method stub
		return false;
	}

}
