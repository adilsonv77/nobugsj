package nobugsj;

import pt.uc.dei.nobugssnackbar.MundoVisual;
import pt.uc.dei.nobugssnackbar.Order;
import pt.uc.dei.nobugssnackbar.SnackMan;

public class M2_5 extends SnackMan {

	@Override
	public void serve() throws Exception {

		goToBarCounter(2);
		
		if (hasHunger()) {
			
			Order comida = askForFood();
			goToDisplay();
			comida = pickUpHotDog(comida);

			goToBarCounter(2);
			deliver(comida);

		}
		
		if (hasThirsty()) {
			
			Order bebida = askForDrink();
			goToCooler();
			bebida = pickUpDrink(bebida);
			
			goToBarCounter(2);
			deliver(bebida);
		}
		
	}

	public static void main(String[] args) {
		MundoVisual.iniciar("mission2_5.dat", M2_5.class);
	}

}
