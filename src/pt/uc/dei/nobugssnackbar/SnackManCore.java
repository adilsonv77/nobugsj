package pt.uc.dei.nobugssnackbar;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import pt.uc.dei.nobugssnackbar.exceptions.MundoException;
import pt.uc.dei.nobugssnackbar.suporte.FinishedRunListener;

public class SnackManCore implements Runnable {

	private boolean podeParar;
	private List<FinishedRunListener> fimListeners = new ArrayList<>();
	private SnackMan snackMan;
	private int initialX;
	private int initialY;
	private int curX, curY;

	public SnackManCore(SnackMan snackMan) {
		this.snackMan = snackMan;
		this.snackMan.setCore(this);
	}

	public void setInitialPosition(String position) {
		if (position.equals("initial")) {
			this.initialX = 270;
			this.initialY = 356;
			
			this.curX = initialX;
			this.curY = initialY;
		}
	}

	public void paint(Graphics g) {
		g.drawImage(this.snackMan.getImage(), curX, curY, null);
		
	}
	
	public void run() {
		podeParar = false;
		MundoException ex = null;
		try {
			esperar(1);
			serve();
		} catch (MundoException e) {
			ex = e;
			e.printStackTrace();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		podeParar = false;
		notifyFinishedRun();
		if (ex != null)
			throw ex;
		else
			return;

	}

	private void esperar(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public void addFinishedRunListener(FinishedRunListener listener) {
		fimListeners.add(listener);
	}
	
	private void notifyFinishedRun() {
		for (FinishedRunListener list:fimListeners)
			list.finished();
	}

	public void parar() {
		this.podeParar = true;
		
	}

	private void serve() throws Exception {
		snackMan.serve();
		
	}


}
