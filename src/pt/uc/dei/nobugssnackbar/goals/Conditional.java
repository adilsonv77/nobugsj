package pt.uc.dei.nobugssnackbar.goals;

import javax.script.ScriptException;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;
import pt.uc.dei.nobugssnackbar.suporte.Scripts;

public class Conditional extends Objective {

	public Conditional(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return this.getConf().getText();
	}


	@Override
	protected boolean doVerifyObjective(Object options) {
		
		try {
			return Scripts.eval(conf.getCondition()).toString().equals("true");
		} catch (ScriptException e) {
			e.printStackTrace();
			
			return false;
		}
	}

}
