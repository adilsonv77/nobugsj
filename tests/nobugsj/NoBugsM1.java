package nobugsj;

import pt.uc.dei.nobugssnackbar.MundoVisual;
import pt.uc.dei.nobugssnackbar.SnackMan;

public class NoBugsM1 extends SnackMan {

	@Override
	public void inteligencia() throws Exception {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) {
		MundoVisual.iniciar("mission01.dat", NoBugsM1.class);
	}

}
