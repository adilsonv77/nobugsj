package pt.uc.dei.nobugssnackbar;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

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

	private MundoVisual mundoVisual;

	private Thread thread;

	private Class<? extends SnackMan> snackManClass;

	private List<Objective> objectives = new ArrayList<>();

	private int runs = 0;

	private Exercicio exercicio;

	private boolean parar;

	private Image boxOfFruits;

	private Image juiceMachine;

	private Image iceCreamMachine;

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
	}

	private void createCustomers(List<CustomerDefinition> customersDef) {

		customers.clear();

		int idx = 0, idxC = 1;
		while (idx < customersDef.size()) {
			CustomerDefinition cust = customersDef.get(idx);
			if (cust.getDest().startsWith("counter") && Integer.parseInt(cust.getDest().substring(7, 8)) != idxC) {
				customers.add(new Customer(null, idxC-1));
				idxC = Integer.parseInt(cust.getDest().substring(7, 8));
			}
			customers.add(new Customer(cust, idxC-1));
			idx++; idxC++;
			
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

	private void createObjectives(List<ObjectiveConf> objectives) throws Exception {
		this.objectives.clear();
		for (ObjectiveConf objconf:objectives) {
			Objective obj = objconf.generate();
			obj.setMundo(this);
			this.objectives.add( obj );
		}
	}

	public void reiniciar() {
		this.runs = 0;
		this.parar = false;
		repaint();
		
	}

	public int getTempoEspera() {
		return snackman.getTempoEspera();
	}

	public void setTempoEspera(int value) {
		snackman.setTempoEspera(value);
	}

	public void executar() {
		this.runs++;
		this.thread = new Thread(this.snackman);
		thread.start();
		
	}

	public void setMundoVisual(MundoVisual mundoVisual) {
		this.mundoVisual = mundoVisual;
	}

	@Override
	public void finished() {
		if (this.thread != null) {
			this.thread.interrupt();
			this.thread = null;
		}
		
		if (!parar && allGoalsAchieved() && this.runs < this.exercicio.getTests()) {
			// next configuration
			
			for (Customer cust:customers)
				cust.nextOrder();
			
			// run the cooker
			try {
				
				createCooker(snackManClass, exercicio.getCooker());
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
		return this.objectives;
	}

	public void verifyObjectives(String key, Object options) {

		for (int i = 0; i < objectives.size(); i++) {
			Objective obj = objectives.get(i);
			if (obj.verifyObjective(key, options)) {
				this.mundoVisual.checkGoal(i);
			}
		}
		
	}
	
	public String readVariableTest(String varName) {
		return this.exercicio.readVariableTest(this.runs-1, varName);
	}

}
