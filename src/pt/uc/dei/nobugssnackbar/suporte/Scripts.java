package pt.uc.dei.nobugssnackbar.suporte;

import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Scripts {

	private static ScriptEngine js;
	
	static {
		js = (new ScriptEngineManager()).getEngineByName("js");
		try {
			js.eval(new FileReader("customermanager.js"));
			js.eval(new FileReader("game.js"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static Object eval(String value) throws ScriptException {
		
		return js.eval(value);
	}
}
