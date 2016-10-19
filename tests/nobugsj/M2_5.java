package nobugsj;

import pt.uc.dei.nobugssnackbar.VisualWorld;
import pt.uc.dei.nobugssnackbar.Order;
import pt.uc.dei.nobugssnackbar.SnackMan;

public class M2_5 extends SnackMan {

	@Override
	public void serve() throws Exception {

		// missão para exemplificar a manipulação com os objetos
		goToBarCounter(2);
		
		Order comida = askForFood();
		Order bebida = askForDrink();

		goToDisplay();
		comida = pickUpHotDog(comida);

		goToCooler();
		bebida = pickUpDrink(bebida);
		
		goToBarCounter(2);
		deliver(comida);
		deliver(bebida);
		
	}

	public static void main(String[] args) {
		VisualWorld.start("mission2_5.dat", M2_5.class);
	}

}
