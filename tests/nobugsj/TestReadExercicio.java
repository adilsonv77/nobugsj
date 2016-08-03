package nobugsj;

import pt.uc.dei.nobugssnackbar.suporte.Customer;
import pt.uc.dei.nobugssnackbar.suporte.Exercicio;
import pt.uc.dei.nobugssnackbar.suporte.ExercicioFactory;
import pt.uc.dei.nobugssnackbar.suporte.Objective;

public class TestReadExercicio {

	public static void main(String[] args) throws Exception {
		Exercicio exerc = ExercicioFactory.create("mission01.dat");
		
		System.out.println(exerc.getExplanation());
		System.out.println(exerc.getCooker());
		
		System.out.println(exerc.getCustomers().size());
		for (Customer cust:exerc.getCustomers())
			System.out.println(cust);
		
		System.out.println(exerc.getObjectives().size());
		for (Objective obj:exerc.getObjectives())
			System.out.println(obj);
	}

}
