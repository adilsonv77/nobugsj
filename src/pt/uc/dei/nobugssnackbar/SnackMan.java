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
	
	public abstract void serve() throws Exception;

	public static final double VERSAO = 0.1;

	SnackManCore getCore() { return core; }
	void setCore(SnackManCore snackManCore) {
		this.core = snackManCore;
	}

}