package pt.uc.dei.nobugssnackbar.goals;

import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class Talk extends Objective {

	public Talk(ObjectiveConf conf) {
		super(conf);
	}

	@Override
	public String getText() {
		return String.format("Falar %s", conf.getText());
	}

	@Override
	protected boolean doVerifyObjective(Object options) {
		
		Object value = "";
		try {
			if (conf.getCondition() != null) {
	
				/*
				if (!eval(objective.condition)) // if it's not necessary to talk
					return options.allCustomers;
					*/
			}
			
			value = js.eval(conf.getValue());
			if (conf.getType() != null)
				if (conf.getType().equals("functionCompare"))
					return Boolean.parseBoolean(value.toString());
			/*
				if (Array.isArray(options.data) && objective.type === "array") {
					if (value.indexOf("##") > -1) {
						
						value = value.split("##");
						if (value.length !== options.data.length)
							return false;
					} else {
						value = [value];
					}
						
					
					for (var i = 0; i<options.data.length; i++) {
						if (JSON.stringify(options.data[i]) !== value[i]+"")
							return false;
					}
					return true;
				}

			 */
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return (value + "").equals(options.toString());
	}

	private static ScriptEngine js;
	
	static {
		js = (new ScriptEngineManager()).getEngineByName("js");
		try {
			js.eval(new FileReader("customermanager.js"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
