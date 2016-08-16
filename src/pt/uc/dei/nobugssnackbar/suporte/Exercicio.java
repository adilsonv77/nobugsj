package pt.uc.dei.nobugssnackbar.suporte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Adilson Vahldick
 *
 */
public class Exercicio
{

	private String classExercicio;
	private String explanation;
	private String cooker;
	private int tests;
	private List<CustomerDefinition> customers = new ArrayList<CustomerDefinition>();
	private List<ObjectiveConf> objectives = new ArrayList<ObjectiveConf>();
	
    public void setClassExercicio(String classExercicio)
    {
         this.classExercicio = classExercicio;
    }

	public Exercicio(String nomeArquivo) {
		
		String[] pedacos = nomeArquivo.split("[.]");
		
		this.classExercicio = "";
		for (int x=0;x<pedacos.length-1;x++) {
			this.classExercicio += pedacos[x];
			if (x <pedacos.length-2)
				this.classExercicio += ".";
		}
		
	}
	
	 public void finalizar() {
		 
	 }
	 
	 public String getExplanation() {
		return explanation;
	}
	 
	 public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	 
	 public String getCooker() {
		return cooker;
	}
	 
	 public void setCooker(String cooker) {
		this.cooker = cooker;
	}
	 
	public int getTests() {
		return tests;
	}
	
	public void setTests(int tests) {
		this.tests = tests;
	}
	 
	public void addCustomer(CustomerDefinition customer) {
		this.customers.add(customer);
	}
	 
	public List<CustomerDefinition> getCustomers() {
		return customers;
	}
	
	public void addObjective(ObjectiveConf obj) {
		this.objectives.add(obj);
	}
	
	public List<ObjectiveConf> getObjectives() {
		return objectives;
	}
	
	private List<Map<String, String>> testVars = new ArrayList<Map<String, String>>();
	
	public void addTest(HashMap<String, String> vars) {
		testVars.add(vars);
	}
	
	public String readVariableTest(int run, String varName) {
		return testVars.get(run).get(varName);
	}

}