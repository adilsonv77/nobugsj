package nobugsj;

import pt.uc.dei.nobugssnackbar.MundoVisual;
import pt.uc.dei.nobugssnackbar.Order;
import pt.uc.dei.nobugssnackbar.SnackMan;

public class NoBugsM1 extends SnackMan {

	@Override
	public void serve() throws Exception {
		
		for (int i = 1; i <= 3; i++) {
			
			goToBarCounter(i);
			if (isThereACustomer()) {
				
				talk("Cheguei no cliente " + i + " com fome de " + askWantHowManyFoods() + " e sede de " + askWantHowManyDrinks());
				if (askHasHunger()) { 
					
					Order food = askForFood();
					talk(food);
					
					goToDisplay();
					
					Order order = pickUpHotDog(food);
					goToBarCounter(i);
					deliver(order);
				}

				if (askHasThirsty()) {
					
					Order drink = askForDrink();
					talk(drink);
					
					goToCooler();
					
					Order order = pickUpDrink(drink);
					goToBarCounter(i);
					deliver(order);
					
					
				}
			}
		}
		

	}
	
	public static void main(String[] args) {
		MundoVisual.iniciar("mission01.dat", NoBugsM1.class);
	}

}
