package pt.uc.dei.nobugssnackbar.goals;

import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;
import pt.uc.dei.nobugssnackbar.suporte.Scripts;

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
			
			value = Scripts.eval(conf.getValue());
			if (conf.getTypeConv() != null) {
				if (conf.getTypeConv().equals("functionCompare"))
					return Boolean.parseBoolean(value.toString());
				if (conf.getTypeConv().equals("int")) {
					value = Math.round((double) value);
				}
			} else
				if (value.getClass() != Integer.class)
					value = Math.round((double) value);
			
			
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

}
