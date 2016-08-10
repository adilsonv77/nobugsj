package pt.uc.dei.nobugssnackbar;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import pt.uc.dei.nobugssnackbar.grafos.Vertice;
import pt.uc.dei.nobugssnackbar.suporte.CustomerDefinition;
import pt.uc.dei.nobugssnackbar.suporte.Exercicio;
import pt.uc.dei.nobugssnackbar.suporte.FinishedRunListener;
import pt.uc.dei.nobugssnackbar.suporte.LoadImage;

public class NoBugsVisual extends JPanel implements FinishedRunListener {
	
	private static final long serialVersionUID = 1L;

	private Image image;

	private SnackManCore snackman;
	private List<Customer> customers = new ArrayList<>();

	private Image display;

	private Image cooler;

	private MundoVisual mundoVisual;

	private Thread thread;

	private Class<? extends SnackMan> snackManClass;
	

	public NoBugsVisual(Exercicio exercicio, Class<? extends SnackMan> snackManClass) throws Exception {
		this.image = LoadImage.getInstance().getImage("imagens/fundo_new2.png");
		this.snackManClass = snackManClass;
		setPreferredSize(new Dimension(360, 440));
		setExercicio(exercicio);
	}

	private void createKitchenFurniture() throws Exception {
		this.display = LoadImage.getInstance().getImage("imagens/display.png");
		this.cooler = LoadImage.getInstance().getImage("imagens/cooler.png");
	}

	private void createCustomers(List<CustomerDefinition> customersDef) {

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

		this.snackman.paint(g);
		
		g.drawImage(this.cooler, 280, 360, null);
		
		try {
			
			for (Customer c:customers)
				c.paint(g);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void parar() {
		this.snackman.parar();
		if (this.thread != null) {
			this.thread.interrupt();
			this.thread = null;
		}
		this.mundoVisual.fimExecucao();
		
	}

	public void setExercicio(Exercicio exercicio) throws Exception {
		createKitchenFurniture();
		createCooker(snackManClass, exercicio.getCooker());
		createCustomers(exercicio.getCustomers());
	}

	public void reiniciar() {
		
		repaint();
		
	}

	public int getTempoEspera() {
		return snackman.getTempoEspera();
	}

	public void setTempoEspera(int value) {
		snackman.setTempoEspera(value);
	}

	public void executar() {
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
		this.mundoVisual.fimExecucao();
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

}
