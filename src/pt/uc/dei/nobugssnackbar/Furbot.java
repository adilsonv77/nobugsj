package pt.uc.dei.nobugssnackbar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import pt.uc.dei.nobugssnackbar.exceptions.MundoException;
import pt.uc.dei.nobugssnackbar.suporte.LoadImage;

/**
 * 
 * @author Adilson Vahldick
 */
public abstract class Furbot extends ObjetoDoMundoAdapter {

	public Furbot() {
	}

	public ImageIcon buildImage() {
		ImageIcon image = LoadImage.getInstance().getIcon("imagens/r2d2-icon.gif");
		
		if (ehDependenteEnergia()) {
			BufferedImage img = new BufferedImage(50, 50, 1);
			Graphics2D g = img.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, 50, 50);
			g.drawImage(image.getImage(), 2, 2, null);
			float fator = (float) getEnergia()
					/ ((float) getMaxEnergia() * 1.0F);
			int altura = Math.round(40F * fator);
			g.setColor(Color.red);
			g.fillRoundRect(40, 45 - altura, 5, altura, 5, 5);
			g.setColor(Color.black);
			g.drawRoundRect(40, 5, 5, 40, 5, 5);
			image = new ImageIcon(img);
		}
		
		return image;
	}

	public void executar() throws Exception {
		MundoException ex = null;
		try {
			esperar(1);
			inteligencia();
		} catch (MundoException e) {
			ex = e;
		}
		objetoMundoImpl.pararMundo();
		if (ex != null)
			throw ex;
		else
			return;
	}

	public abstract void inteligencia() throws Exception;

	public static final double VERSAO = 1.8;
}