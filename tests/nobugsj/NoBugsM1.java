package nobugsj;

import pt.uc.dei.nobugssnackbar.MundoVisual;
import pt.uc.dei.nobugssnackbar.SnackMan;

public class NoBugsM1 extends SnackMan {

	@Override
	public void serve() throws Exception {
		
		System.out.println("FOI");

	}
	
	public static void main(String[] args) {
		MundoVisual.iniciar("mission01.dat", NoBugsM1.class);
	}

}
