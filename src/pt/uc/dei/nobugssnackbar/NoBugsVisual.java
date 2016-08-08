package pt.uc.dei.nobugssnackbar;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import pt.uc.dei.nobugssnackbar.suporte.CustomerDefinition;
import pt.uc.dei.nobugssnackbar.suporte.Exercicio;
import pt.uc.dei.nobugssnackbar.suporte.LoadImage;

public class NoBugsVisual extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private Image image;

	private SnackMan snackman;
	private List<Customer> customers = new ArrayList<>();
	

	public NoBugsVisual(Exercicio exercicio, Class<? extends SnackMan> snackman) throws Exception {
		this.image = LoadImage.getInstance().getImage("imagens/fundo_new2.png");
		setPreferredSize(new Dimension(360, 440));
		
		createCooker(snackman, exercicio.getCooker());
		createCustomers(exercicio.getCustomers());
	}

	private void createCustomers(List<CustomerDefinition> customersDef) {

		for (CustomerDefinition cust:customersDef) {
			customers.add(new Customer(cust));
		}
		
	}

	private void createCooker(Class<? extends SnackMan> snackman, String cooker) throws Exception {
		
		this.snackman = snackman.newInstance();
		this.snackman.setInitialPosition(cooker);
		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(this.image, 0, 0, null);
		this.snackman.paint(g);
		for (Customer c:customers)
			c.paint(g);
	}

	public void parar() {
		// TODO Auto-generated method stub
		
	}

	public void setExercicio(Exercicio exercicio) {
		// TODO Auto-generated method stub
		
	}

	public void reiniciar() {
		// TODO Auto-generated method stub
		
	}

	public int getTempoEspera() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setTempoEspera(int value) {
		// TODO Auto-generated method stub
		
	}

	public void executar() {
		// TODO Auto-generated method stub
		
	}

}
