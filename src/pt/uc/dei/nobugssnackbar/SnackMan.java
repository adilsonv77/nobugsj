package pt.uc.dei.nobugssnackbar;

import java.awt.Graphics;
import java.awt.Image;

import pt.uc.dei.nobugssnackbar.exceptions.MundoException;
import pt.uc.dei.nobugssnackbar.suporte.LoadImage;

/**
 * 
 * @author Adilson Vahldick
 */
public abstract class SnackMan  {

	private Image image;
	private int initialX;
	private int initialY;

	public SnackMan() {
		try {
			buildImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setInitialPosition(String position) {
		if (position.equals("initial")) {
			this.initialX = 270;
			this.initialY = 356;
		}
		
		
	}
	

	public Image buildImage() throws Exception {
		if (this.image == null)
			this.image = LoadImage.getInstance().getImage("imagens/snackman.png");
		
		return this.image;
	}

	public void paint(Graphics g) {
		g.drawImage(image, initialX, initialY, null);
		
	}
	
	private void esperar(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void executar() throws Exception {
		MundoException ex = null;
		try {
			esperar(1);
			inteligencia();
		} catch (MundoException e) {
			ex = e;
		}
		if (ex != null)
			throw ex;
		else
			return;
	}

	public abstract void inteligencia() throws Exception;

	public static final double VERSAO = 0.1;

}