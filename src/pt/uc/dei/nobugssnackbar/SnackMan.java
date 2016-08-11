package pt.uc.dei.nobugssnackbar;

import java.awt.Image;

import pt.uc.dei.nobugssnackbar.suporte.LoadImage;

/**
 * 
 * @author Adilson Vahldick
 */
public abstract class SnackMan {

	private Image image;
	private SnackManCore core;

	public SnackMan() {
		new SnackManCore(this);
	}
	
	public Image getImage() {
		if (this.image == null)
			try {
				this.image = LoadImage.getInstance().getImage("imagens/snackman.png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return this.image;
	}
	
	public final void goToBarCounter(int counter) throws Exception {
		core.goToBarCounter(counter);
	}

	public final void goToDisplay() throws Exception {
		core.goToDisplay();
	}
	
	public final void goToCooler() throws Exception {
		core.goToCooler();
	} 
	
	public final void talk(Object text) throws Exception {
		core.talk(text);
	} 
	
	public final Order askForFood() throws Exception {
		return core.askForFood();
	}
	
	public final boolean hasHunger() throws Exception {
		return core.hasHunger();
	}
	
	public final Order pickUpHotDog(Order food) {
		return core.pickUpHotDog(food);
	}

	public final Order askForDrink() throws Exception {
		return core.askForDrink();
	}
	
	public final boolean hasThirsty() throws Exception {
		return core.hasThirsty();
	}
	
	public final Order pickUpDrink(Order drink) {
		return core.pickUpDrink(drink);
	}

	public final boolean isThereACustomer() throws Exception {
		return core.isThereACustomer();
	}
	
	public final void deliver(Order order) {
		core.deliver(order);
	}

	
	public abstract void serve() throws Exception;

	public static final double VERSAO = 0.1;

	SnackManCore getCore() { return core; }
	void setCore(SnackManCore snackManCore) {
		this.core = snackManCore;
	}

}