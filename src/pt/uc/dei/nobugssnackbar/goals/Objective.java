package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.NoBugsVisual;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public abstract class Objective {
	
	protected ObjectiveConf conf;
	private NoBugsVisual noBugsVisual;

	public Objective(ObjectiveConf conf) {
		this.conf = conf;
	}
	
	public abstract String getText();

	public boolean verifyObjective(String key, Object options) {

		if (!key.equals(conf.getType())) {
			return false;
		}
		
		return doVerifyObjective(options);
	}

	protected abstract boolean doVerifyObjective(Object options);

	public NoBugsVisual getMundo() {
		return this.noBugsVisual;
	}
	
	public void setMundo(NoBugsVisual noBugsVisual) {
		this.noBugsVisual = noBugsVisual;		
	}

}
