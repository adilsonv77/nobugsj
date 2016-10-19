package pt.uc.dei.nobugssnackbar;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import pt.uc.dei.nobugssnackbar.goals.Objective;
import pt.uc.dei.nobugssnackbar.grafos.Vertice;
import pt.uc.dei.nobugssnackbar.suporte.CustomerDefinition;
import pt.uc.dei.nobugssnackbar.suporte.Exercicio;
import pt.uc.dei.nobugssnackbar.suporte.FinishedRunListener;
import pt.uc.dei.nobugssnackbar.suporte.LoadImage;
import pt.uc.dei.nobugssnackbar.suporte.ObjectiveConf;

public class NoBugsVisual extends JPanel implements FinishedRunListener {
	
	public static NoBugsVisual instance = null;
	
	private static final long serialVersionUID = 1L;

	private Image image;

	private SnackManCore snackman;
	private List<Customer> customers = new ArrayList<>();

	private Image display;

	private Image cooler;

	private VisualWorld mundoVisual;

	private Thread thread;

	private Class<? extends SnackMan> snackManClass;

	private static List<Objective> objectives = new ArrayList<>();

	private int runs = 0;

	private Exercicio exercicio;

	private boolean parar;

	private Image boxOfFruits;

	private Image juiceMachine;

	private Image iceCreamMachine;

	private Image coffeeMachine;

	private int idxCoffeeMachine;

	private int tempoEspera;

	public NoBugsVisual(Exercicio exercicio, Class<? extends SnackMan> snackManClass) throws Exception {
		instance = this;
		
		this.image = LoadImage.getInstance().getImage("imagens/fundo_new2.png");
		this.snackManClass = snackManClass;
		
		CustomerManager.setCustomers(this.customers);
		
		setPreferredSize(new Dimension(360, 440));
		setExercicio(exercicio);
	}

	private void createKitchenFurniture() throws Exception {
		if (this.display != null)
			return;
		
		this.display = LoadImage.getInstance().getImage("imagens/display.png");
		this.cooler = LoadImage.getInstance().getImage("imagens/cooler.png");
		this.boxOfFruits = LoadImage.getInstance().getImage("imagens/boxoffruits.png");
		this.juiceMachine = LoadImage.getInstance().getImage("imagens/juicemachine.png");
		this.iceCreamMachine = LoadImage.getInstance().getImage("imagens/icecreammachine.png");
		
		this.coffeeMachine = LoadImage.getInstance().getImage("imagens/coffeemachine.png");
		this.idxCoffeeMachine = 0;
	}

	private void createCustomers(List<CustomerDefinition> customersDef) {

		customers.clear();

		int idx = 0;
		for (int i = 1; i <= 3; i++) {
			
			if (idx < customersDef.size()) {
				CustomerDefinition cust = customersDef.get(idx);
				if (Integer.parseInt(cust.getDest().substring(7, 8)) != i) 
					customers.add(new Customer(null, i-1));
				else {
					customers.add(new Customer(cust, i-1));
					idx++;
				}
			} else
				customers.add(new Customer(null, i-1));
		}
		
		
		
	}

	private void createCooker(Class<? extends SnackMan> snackman, String cooker) throws Exception {
		
		this.snackman = snackman.newInstance().getCore();
		this.snackman.setInitialPosition(cooker);
		this.snackman.addFinishedRunListener(this);
		this.snackman.setMundo(this);
		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(this.image, 0, 0, null);
		
		g.drawImage(this.display, 200, 200, null);
		g.drawImage(this.juiceMachine, 320, 190, null);
		g.drawImage(this.iceCreamMachine, 160, 160, null);
		
		g.drawImage(this.coffeeMachine, 130, 190, 130+30, 190+40, idxCoffeeMachine*30, 0, (idxCoffeeMachine*30)+30, 40, null);
		
		this.snackman.paint(g);
		
		g.drawImage(this.cooler, 280, 360, null);
		g.drawImage(this.boxOfFruits, 200, 408, null);
		
		try {
			
			for (Customer c:customers)
				c.paint(g);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void nextCoffeeMachineImg() {
		idxCoffeeMachine = (idxCoffeeMachine+1)%3;
		repaint();
	}
	
	public void resetCoffeeMachineImg() {
		idxCoffeeMachine = 0;
		repaint();
	}
	
	public int getIdxCoffeeMachine() {
		return idxCoffeeMachine;
	}
	
	public boolean isParar() {
		return parar;
	}
	
	public void parar() {
		this.parar = true;
		this.snackman.parar();
		if (this.thread != null) {
			this.thread.interrupt();
			this.thread = null;
		}
		this.mundoVisual.fimExecucao();
		
	}

	public void setExercicio(Exercicio exercicio) throws Exception {
		this.exercicio = exercicio;
		
		createKitchenFurniture();
		createCooker(snackManClass, exercicio.getCooker());
		createCustomers(exercicio.getCustomers());
		createObjectives(exercicio.getObjectives());
	}

	private void createObjectives(List<ObjectiveConf> paramObjectives) throws Exception {
		objectives.clear();
		for (ObjectiveConf objconf:paramObjectives) {
			Objective obj = objconf.generate();
			obj.setMundo(this);
			objectives.add( obj );
		}
	}

	public void reiniciar() {
		this.runs = 0;
		this.parar = false;
		this.idxCoffeeMachine = 0;
		repaint();
		
	}

	public int getTempoEspera() {
		return snackman.getTempoEspera();
	}

	public void setTempoEspera(int value) {
		this.tempoEspera = value;
		snackman.setTempoEspera(value);
	}

	public void executar() {
		this.runs++;
		this.thread = new Thread(this.snackman);
		thread.start();
		
	}

	public void setMundoVisual(VisualWorld mundoVisual) {
		this.mundoVisual = mundoVisual;
	}

	public boolean isTeveExcecao() {
		return this.snackman.isTeveExcecao();
	}
	
	@Override
	public void finished() {
		if (this.thread != null) {
			this.thread.interrupt();
			this.thread = null;
		}
		
		
		verifyObjectivesForAllCustomers("deliver"); 
		verifyObjectivesForAllCustomers("customDeliver"); 
		verifyObjectivesForAllCustomers("deliverGifts");
		verifyObjectivesForAllCustomers("conditional"); 
		
		if (!parar && allGoalsAchieved() && this.runs < this.exercicio.getTests()) {
			// next configuration
			
			for (Customer cust:customers)
				cust.nextOrder();
			
			// run the cooker
			try {
				
				this.idxCoffeeMachine = 0;
				createCooker(snackManClass, exercicio.getCooker());
				this.snackman.setTempoEspera(tempoEspera);
				this.mundoVisual.fimExecucaoIntermediaria(this.runs);
								
			} catch (Exception e) {
				e.printStackTrace();
				this.mundoVisual.fimExecucao();
				return;
			}
			
			repaint();
			executar();
			
			return;
		}
		
		this.mundoVisual.fimExecucao();
	}

	private void verifyObjectivesForAllCustomers(String key) {

		for(Customer cust:customers) {
			if (cust.getPlace() != null)
				verifyObjectives(key, cust);
		}
		
	}

		
	private boolean allGoalsAchieved() {
		return this.mundoVisual.allGoalsAchieved();
	}

	public void changeSnackManPosition(Vertice vertice) {
		
		this.snackman.changeSnackManPosition(vertice);
		
		repaint();
		
	}
	
	public void addConsole(Object text) {
		this.mundoVisual.addTextConsole(text);
	}

	public Customer getCustomerCounter(int counterIndex) {
		Customer cust = customers.get(counterIndex-1);
		if (cust != null && cust.isThereIsACustomer())
			return cust;
		
		return null;
	}

	public List<Objective> getObjectives() {
		return objectives;
	}

	public static Objective search(String key, Map<String, String> params) throws Exception {
		
		for (Objective obj:objectives) {
			
			ObjectiveConf conf = obj.getConf();
			if (conf.getType().equals(key)) {
				boolean found = true;
				for (String kp:params.keySet()) {
					
					String methodName = "get" + kp.substring(0,1).toUpperCase() + kp.substring(1);
					
					String value = ObjectiveConf.class.getMethod(methodName).invoke(conf) + "";
					if (!value.equals(params.get(kp))) {
						found = false;
						break;
					}
				}
				if (found)
					return obj;
			}
				
		}
		
		return null;
	}
	
	public void verifyObjectives(String key, Object options) {

		boolean isCustomer = options != null && options.getClass() == Customer.class;
		
		
		for (int i = 0; i < objectives.size(); i++) {
			Objective obj = objectives.get(i);
			
			if (isCustomer && obj.getConf().getPlace() != null) {
				Customer cust = (Customer)options;
				if (!(cust.getPos() == obj.getConf().getPos() && cust.getPlace().equals(obj.getConf().getPlace()))) {
					continue;
				}
			}
			
			if (obj.verifyObjective(key, options)) {
				this.mundoVisual.checkGoal(i);
			}
		}
		
	}
	
	public String readVariableTest(String varName) {
		return this.exercicio.readVariableTest(this.runs-1, varName);
	}

}
